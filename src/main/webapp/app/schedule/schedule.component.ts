import { ChangeDetectionStrategy, Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { endOfDay, endOfMonth, format, isSameDay, isSameMonth, startOfDay } from 'date-fns';
import { Subject } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CalendarEvent, CalendarEventAction, CalendarEventTimesChangedEvent, CalendarView } from 'angular-calendar';
import { ScheduleService } from 'app/entities/schedule';
import { Router } from '@angular/router';

interface Film {
    id: number;
    title: string;
    release_date: string;
}

const colors: any = {
    red: {
        primary: '#ad2121',
        secondary: '#FAE3E3'
    },
    blue: {
        primary: '#1e90ff',
        secondary: '#D1E8FF'
    },
    yellow: {
        primary: '#e3bc08',
        secondary: '#FDF1BA'
    }
};

@Component({
    selector: 'jhi-schedule',
    changeDetection: ChangeDetectionStrategy.OnPush,
    templateUrl: './schedule.component.html',
    styleUrls: ['./styles.css']
})
export class ScheduleComponent implements OnInit {
    @ViewChild('modalContent') modalContent: TemplateRef<any>;

    view: CalendarView = CalendarView.Month;

    CalendarView = CalendarView;

    viewDate: Date = new Date();

    modalData: {
        action: string;
        event: CalendarEvent;
    };

    actions: CalendarEventAction[] = [
        {
            label: '✏️',
            onClick: ({ event }: { event: CalendarEvent }): void => {
                this.handleEvent('edit', event);
            }
        },
        {
            label: '❌',
            onClick: ({ event }: { event: CalendarEvent }): void => {
                this.handleEvent('delete', event);
            }
        }
    ];

    refresh: Subject<any> = new Subject();

    events: CalendarEvent[] = [];

    activeDayIsOpen = true;

    constructor(private modal: NgbModal, private scheduleService: ScheduleService, private router: Router) {}

    ngOnInit() {
        this.populateCalendar();
    }

    dayClicked({ date, events }: { date: Date; events: CalendarEvent[] }): void {
        if (isSameMonth(date, this.viewDate)) {
            if ((isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) || events.length === 0) {
                this.activeDayIsOpen = false;
                // if no events, clicking on day brings up add points
                if (events.length === 0) {
                    this.router.navigateByUrl('/schedule/new?date=' + format(date, 'YYYY-MM-DD'));
                }
            } else {
                this.activeDayIsOpen = true;
                this.viewDate = date;
            }
        }
    }

    populateCalendar() {
        const monthEnd = endOfMonth(this.viewDate);
        const month = format(monthEnd, 'YYYY-MM');

        this.scheduleService.findByMonth(month).subscribe((response: any) => {
            response.body.forEach(item => {
                this.events.push({
                    start: startOfDay(item.date),
                    end: endOfDay(item.date),
                    title: item.mealName,
                    color: colors.green,
                    draggable: true,
                    actions: this.actions,
                    meta: {
                        id: item.id,
                        entity: 'schedule',
                        mealId: item.mealId
                    }
                });
            });
            this.refresh.next();
        });
    }

    eventTimesChanged({ event, newStart, newEnd }: CalendarEventTimesChangedEvent): void {
        event.start = newStart;
        event.end = newEnd;
        this.handleEvent('Dropped or resized', event);
        this.refresh.next();
    }

    handleEvent(action: string, event: CalendarEvent): void {
        action = action === 'Clicked' ? 'view' : action;
        this.modalData = { event, action };
        let url = this.router.createUrlTree(['/' + 'meal', event.meta.mealId, 'view']);
        if (action === 'edit') {
            url = this.router.createUrlTree(['/' + event.meta.entity, event.meta.id, 'edit']);
        } else if (action === 'delete') {
            url = this.router.createUrlTree(['/', { outlets: { popup: 'schedule/' + event.meta.id + '/delete' } }]);
        }
        this.router.navigateByUrl(url.toString());
    }
}

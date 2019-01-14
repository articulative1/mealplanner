import { ScheduleComponent } from 'app/schedule/schedule.component';
import { Route } from '@angular/router';

export const scheduleRoute: Route = {
    path: 'testschedule',
    component: ScheduleComponent,
    data: {
        authorities: [],
        pageTitle: 'Schedule'
    }
};

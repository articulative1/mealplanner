import { NgModule } from '@angular/core';
import { MealplannerSharedModule } from 'app/shared';
import { scheduleRoute } from 'app/schedule/schedule.route';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { ScheduleComponent } from 'app/schedule/schedule.component';
import { FlatpickrModule } from 'angularx-flatpickr';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
    imports: [
        MealplannerSharedModule,
        FormsModule,
        NgbModalModule,
        FlatpickrModule.forRoot(),
        BrowserAnimationsModule,
        CalendarModule.forRoot({
            provide: DateAdapter,
            useFactory: adapterFactory
        }),
        RouterModule.forRoot([scheduleRoute], { useHash: true })
    ],
    declarations: [ScheduleComponent],
    entryComponents: [],
    providers: []
})
export class ScheduleModule {}

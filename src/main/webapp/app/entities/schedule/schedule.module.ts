import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MealplannerSharedModule } from 'app/shared';
import {
    ScheduleComponent,
    ScheduleDeleteDialogComponent,
    ScheduleDeletePopupComponent,
    ScheduleDetailComponent,
    schedulePopupRoute,
    scheduleRoute,
    ScheduleUpdateComponent
} from './';

const ENTITY_STATES = [...scheduleRoute, ...schedulePopupRoute];

@NgModule({
    imports: [MealplannerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ScheduleComponent,
        ScheduleDetailComponent,
        ScheduleUpdateComponent,
        ScheduleDeleteDialogComponent,
        ScheduleDeletePopupComponent
    ],
    entryComponents: [ScheduleComponent, ScheduleUpdateComponent, ScheduleDeleteDialogComponent, ScheduleDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MealplannerScheduleModule {}

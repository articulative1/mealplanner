import { NgModule } from '@angular/core';

import { MealplannerSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [MealplannerSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [MealplannerSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class MealplannerSharedCommonModule {}

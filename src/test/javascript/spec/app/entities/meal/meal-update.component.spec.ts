/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { MealplannerTestModule } from '../../../test.module';
import { MealUpdateComponent } from 'app/entities/meal/meal-update.component';
import { MealService } from 'app/entities/meal/meal.service';
import { Meal } from 'app/shared/model/meal.model';

describe('Component Tests', () => {
    describe('Meal Management Update Component', () => {
        let comp: MealUpdateComponent;
        let fixture: ComponentFixture<MealUpdateComponent>;
        let service: MealService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MealplannerTestModule],
                declarations: [MealUpdateComponent]
            })
                .overrideTemplate(MealUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MealUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MealService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Meal(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.meal = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Meal();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.meal = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});

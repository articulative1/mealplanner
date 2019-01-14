/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MealplannerTestModule } from '../../../test.module';
import { RecipeIngredientUpdateComponent } from 'app/entities/recipe-ingredient/recipe-ingredient-update.component';
import { RecipeIngredientService } from 'app/entities/recipe-ingredient/recipe-ingredient.service';
import { RecipeIngredient } from 'app/shared/model/recipe-ingredient.model';

describe('Component Tests', () => {
    describe('RecipeIngredient Management Update Component', () => {
        let comp: RecipeIngredientUpdateComponent;
        let fixture: ComponentFixture<RecipeIngredientUpdateComponent>;
        let service: RecipeIngredientService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MealplannerTestModule],
                declarations: [RecipeIngredientUpdateComponent]
            })
                .overrideTemplate(RecipeIngredientUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RecipeIngredientUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecipeIngredientService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new RecipeIngredient(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.recipeIngredient = entity;
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
                    const entity = new RecipeIngredient();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.recipeIngredient = entity;
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

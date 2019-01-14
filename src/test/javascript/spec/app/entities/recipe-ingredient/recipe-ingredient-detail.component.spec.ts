/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MealplannerTestModule } from '../../../test.module';
import { RecipeIngredientDetailComponent } from 'app/entities/recipe-ingredient/recipe-ingredient-detail.component';
import { RecipeIngredient } from 'app/shared/model/recipe-ingredient.model';

describe('Component Tests', () => {
    describe('RecipeIngredient Management Detail Component', () => {
        let comp: RecipeIngredientDetailComponent;
        let fixture: ComponentFixture<RecipeIngredientDetailComponent>;
        const route = ({ data: of({ recipeIngredient: new RecipeIngredient(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MealplannerTestModule],
                declarations: [RecipeIngredientDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RecipeIngredientDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RecipeIngredientDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.recipeIngredient).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

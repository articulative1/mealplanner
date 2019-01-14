/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MealplannerTestModule } from '../../../test.module';
import { RecipeIngredientDeleteDialogComponent } from 'app/entities/recipe-ingredient/recipe-ingredient-delete-dialog.component';
import { RecipeIngredientService } from 'app/entities/recipe-ingredient/recipe-ingredient.service';

describe('Component Tests', () => {
    describe('RecipeIngredient Management Delete Component', () => {
        let comp: RecipeIngredientDeleteDialogComponent;
        let fixture: ComponentFixture<RecipeIngredientDeleteDialogComponent>;
        let service: RecipeIngredientService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MealplannerTestModule],
                declarations: [RecipeIngredientDeleteDialogComponent]
            })
                .overrideTemplate(RecipeIngredientDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RecipeIngredientDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecipeIngredientService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});

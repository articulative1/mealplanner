import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecipeIngredient } from 'app/shared/model/recipe-ingredient.model';
import { RecipeIngredientService } from './recipe-ingredient.service';

@Component({
    selector: 'jhi-recipe-ingredient-delete-dialog',
    templateUrl: './recipe-ingredient-delete-dialog.component.html'
})
export class RecipeIngredientDeleteDialogComponent {
    recipeIngredient: IRecipeIngredient;

    constructor(
        protected recipeIngredientService: RecipeIngredientService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.recipeIngredientService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'recipeIngredientListModification',
                content: 'Deleted an recipeIngredient'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-recipe-ingredient-delete-popup',
    template: ''
})
export class RecipeIngredientDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recipeIngredient }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RecipeIngredientDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.recipeIngredient = recipeIngredient;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}

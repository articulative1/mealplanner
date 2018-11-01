import { Component, OnInit } from '@angular/core';
import { MealService } from 'app/entities/meal';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { IMeal } from 'app/shared/model/meal.model';
import { Principal } from 'app/core';

@Component({
    selector: 'jhi-meal-random',
    templateUrl: './meal-random.component.html',
    styles: []
})
export class MealRandomComponent implements OnInit {
    meal: IMeal;

    constructor(
        private mealService: MealService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal
    ) {}

    ngOnInit() {
        this.random();
    }

    random() {
        this.mealService
            .random()
            .subscribe(
                (res: HttpResponse<IMeal>) => this.getMeal(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    reset() {
        this.random();
    }

    private getMeal(data: IMeal, headers: HttpHeaders) {
        this.meal = data;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

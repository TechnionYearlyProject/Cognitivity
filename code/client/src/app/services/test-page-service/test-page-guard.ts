import { Injectable } from '@angular/core';
import { CanDeactivate, Router } from '@angular/router';
import { TestFinishComponent } from '../../components/test-page/test-finish/test-finish.component';
import { DashboardComponent } from '../../components/dashboard/dashboard.component';

@Injectable()
export class TestPageGuard implements CanDeactivate<DashboardComponent> {
  constructor(
    private router: Router
  ) {}

  canDeactivate(target: DashboardComponent) {
    return true;
  }
}
import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { UserService } from './user.service';
import { User } from '../interfaces/user.interface';
import { Observable, of } from 'rxjs';

describe('UserService', () => {
  let service: UserService;
  let http: HttpTestingController;
  let userMock: User = {
    id: 1,
    email: 'test@test@.fr',
    lastName: 'test',
    firstName: 'test',
    admin: true,
    password: 'test123',
    createdAt: new Date(2019, 1, 2, 12, 34, 56),
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, HttpClientTestingModule],
    });
    service = TestBed.inject(UserService);
    http = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should delete a user', () => {
    const id = '1';
    service.delete(id).subscribe((res) => {
      expect(res).toEqual(Observable<any>);
    });
    const req = http.expectOne({
      method: 'DELETE',
      url: `api/user/${id}`,
    });
  });

  it(' should get a user by id', () => {
    const id = '1';
    service.getById(id).subscribe((res) => {
      expect(res).toEqual(userMock)
      ;

    });
    const req = http.expectOne({
      method: 'GET',
      url: `api/user/${id}`,
    });
    req.flush(userMock);
  });
});

import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { UserService } from './user.service';
import { User } from '../interfaces/user.interface';
import { Observable, of } from 'rxjs';
import { any } from 'cypress/types/bluebird';

describe('UserService', () => {
  let service: UserService;
  let httpClientSpy: any;
  let userMock : User ={
  id: 1,
  email: 'test@test@.fr',
  lastName: 'test',
  firstName: 'test',
  admin: true,
  password: 'test123',
  createdAt: new Date(2019,1,2, 12,34,56),  
  }

  beforeEach(() => {
    httpClientSpy = {
      getById : jest.fn(),
      delete: jest.fn()
    }
    TestBed.configureTestingModule({
      imports:[
        HttpClientModule
      ]
    });
    service = TestBed.inject(UserService);
    service = new UserService(httpClientSpy);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  it('', () =>{
    jest.spyOn(httpClientSpy, 'delete').mockReturnValue(of(any));
    service.delete('1');
    expect(httpClientSpy.delete).toBeCalledTimes(1);

  })
  it('should get a user', ()=>{
    jest.spyOn(httpClientSpy, 'getById').mockReturnValue(of(userMock));
    service.getById('1');
    expect(httpClientSpy.get).toBeCalledTimes(1);
  })
});

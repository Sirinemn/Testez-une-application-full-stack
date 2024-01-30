import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { SessionApiService } from './session-api.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Session } from '../interfaces/session.interface';
import { Observable } from 'rxjs';

describe('SessionsService', () => {
  let service: SessionApiService;
  let httpController: HttpTestingController;
  let mockSessions : Session[] =[{
    id: 1,
    name: 'test1',
    description: 'testDescription',
    date: new Date(2024, 1, 1, 11, 34, 56),
    teacher_id: 1,
    users: [1,2]
  },{
    id: 2,
    name: 'test2',
    description: 'testDescription',
    date: new Date(2024, 1, 2, 10, 34, 56),
    teacher_id: 2,
    users: [2,3]
  }
]
let mockSession= {
  id: 3,
  name: 'test3',
  description: 'testDescription',
  date: new Date(2024, 1, 3, 10, 34, 56),
  teacher_id: 3,
  users: [4,3]
}
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        HttpClientModule,
        HttpClientTestingModule
      ]
    });
    service = TestBed.inject(SessionApiService);
    httpController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should call get all sessions', () => {
    service.all().subscribe((res) => {
      expect(res).toEqual(mockSessions);
    });
    const req = httpController.expectOne({
      method: 'GET',
      url: 'api/session',
    });
    req.flush(mockSessions);
  });
  it('should call get sessions by id', () => {
    let id= '1';
    service.detail(id).subscribe((res) => {
      expect(res).toEqual(mockSession);
    });
    const req = httpController.expectOne({
      method: 'GET',
      url: `api/session/${id}`,
    });
    req.flush(mockSession);
  });
  it('should call create sessions', () => {
    service.create(mockSession).subscribe((res) => {
      expect(res).toEqual(mockSession);
    });
    const req = httpController.expectOne({
      method: 'POST',
      url: `api/session`,
    });
    req.flush(mockSession);
  });
  it('should call create sessions', () => {
    const id = ""+mockSession.id
    service.delete(id).subscribe((res) => {
      expect(res).toEqual(Observable<any>);
    });
    const req = httpController.expectOne({
      method: 'DELETE',
      url: `api/session/${id}`,
    });
    req.flush(mockSession);
  });
  it('should call update sessions', () => {
    const mockUpdateSession= {
      id: 3,
      name: 'test4',
      description: 'testDescription',
      date: new Date(2024, 1, 4, 10, 34, 56),
      teacher_id: 3,
      users: [5,6]
    }
    let id = ''+mockSession.id
    service.update(id,mockUpdateSession).subscribe((res) => {
      expect(res).toEqual(mockUpdateSession);
    });
    const req = httpController.expectOne({
      method: 'PUT',
      url: `api/session/${id}`,
    });
    req.flush(mockUpdateSession);
  });
});

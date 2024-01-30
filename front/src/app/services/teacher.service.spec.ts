import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { TeacherService } from './teacher.service';
import { Observable, of } from 'rxjs';
import { Teacher } from '../interfaces/teacher.interface';

describe('TeacherService', () => {
  let service: TeacherService;
  let httpClientSpy: any;
  let teachers: Observable<Teacher[]>;
  let teacher: Observable<Teacher>;
  beforeEach(() => {
    httpClientSpy = {
      all : jest.fn(),
      detail: jest.fn()
    }
    TestBed.configureTestingModule({
      imports:[
        HttpClientModule
      ]
    });
    //service = TestBed.inject(TeacherService);
    service = new TeacherService(httpClientSpy);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  it('should get all teachers', () =>{
    jest.spyOn(httpClientSpy, 'all').mockReturnValue(of(teachers));
    service.all();
    expect(httpClientSpy.get).toBeCalledTimes(1);
    //expect(httpClientSpy.get).toHaveBeenCalledWith('http://localhost:8080api/teacher');

  })
  it('', ()=>{
    jest.spyOn(httpClientSpy, 'detail').mockReturnValue(of(teacher));
    service.detail('1');
    expect(httpClientSpy.get).toBeCalledTimes(1);
    //expect(httpClientSpy.get).toHaveBeenCalledWith('http://localhost:8080api/teacher/1');
  })
});

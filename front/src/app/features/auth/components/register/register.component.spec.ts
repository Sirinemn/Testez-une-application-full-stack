import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { expect } from '@jest/globals';

import { RegisterComponent } from './register.component';
import { RegisterRequest } from '../../interfaces/registerRequest.interface';
import { AuthService } from '../../services/auth.service';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let mockRegisterRequest: RegisterRequest = {
    email: 'test@test.fr',
    firstName: 'mockFirstName',
    lastName: 'mockLastName',
    password: 'test123'
  };
  let authService: AuthService;


  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      imports: [
        BrowserAnimationsModule,
        HttpClientModule,
        ReactiveFormsModule,  
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    authService = TestBed.inject(AuthService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  function updateForm(email : string, firstName: string,lastName: string, password: string){
    component.form.controls['email'].setValue(email);
    component.form.controls['password'].setValue(password);
    component.form.controls['firstName'].setValue(firstName);
    component.form.controls['lastName'].setValue(lastName);
  }
  it('', ()=>{
    //Given
    updateForm('test@test.fr','mockFirstName','mockLastName','test123');
    let authServiceSpy = jest.spyOn(authService, 'register');
    //When
    component.submit()
    //Then
    expect(authServiceSpy).toHaveBeenCalledWith(mockRegisterRequest);
  })
});

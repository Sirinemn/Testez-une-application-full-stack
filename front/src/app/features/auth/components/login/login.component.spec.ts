import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';

import { LoginComponent } from './login.component';
import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../interfaces/loginRequest.interface';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { of } from 'rxjs';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: AuthService;
  let sessionService: SessionService;
  let mockSessionInformation: SessionInformation = {
    token: 'azertyuiop',
    type: 'mockType',
    id: 1,
    username: 'mockUserName',
    firstName: 'mockFirstName',
    lastName: 'mockLastName',
    admin: false,
  };
  let mockLoginRequest: LoginRequest = {
    email: 'sirine@mail.fr',
    password: 'Sirine123',
  };
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [SessionService, AuthService],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule]
    })
      .compileComponents();
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    authService = TestBed.inject(AuthService);
    sessionService = TestBed.inject(SessionService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
    function updateForm(userEmail : string, userPassword: string){
    component.form.controls['email'].setValue(userEmail);
    component.form.controls['password'].setValue(userPassword);
  }

  it('should call login et logIn methode', ()=>{
    //Given
    updateForm('sirine@mail.fr','Sirine123');
    let sessionServiceSpy = jest.spyOn(sessionService, 'logIn');
    let authServiceSpy = jest.spyOn(authService,'login').mockReturnValue(of(mockSessionInformation));
    //when
    component.submit();
    //then
    expect(authServiceSpy).toHaveBeenCalledWith(mockLoginRequest);
    expect(sessionServiceSpy).toHaveBeenCalled();
  })

});

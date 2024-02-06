// ***********************************************
// This example namespace declaration will help
// with Intellisense and code completion in your
// IDE or Text Editor.
// ***********************************************
 declare namespace Cypress {
   interface Chainable<Subject = any> {
     login(email: string, password: string): typeof login;
   }
}
//
 function login(email: string, password: string): void {
    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true
      },
    })

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      [  {
        "id": 1,
        "name": "Basic Asana",
        "date": "2024-01-17T00:00:00.000+00:00",
        "teacher_id": 1,
        "description": "Traditional yoga : 41 Basic Asana, standing and Sitting poses.",
        "users": [],
        "createdAt": "2024-01-16T23:11:25",
        "updatedAt": "2024-01-16T23:11:25"
    },
    {
        "id": 2,
        "name": "Gentle Yoga",
        "date": "2024-01-29T00:00:00.000+00:00",
        "teacher_id": 1,
        "description": "Gentle yoga for beginners",
        "users": [],
        "createdAt": "2024-01-28T18:30:51",
        "updatedAt": "2024-01-28T18:30:51"
    }]).as('session')

    cy.get('input[formControlName=email]').type(email)
    cy.get('input[formControlName=password]').type(`${password}{enter}{enter}`)
}

Cypress.Commands.add('login', login);
//
// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add("login", (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add("drag", { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add("dismiss", { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite("visit", (originalFn, url, options) => { ... })

///<reference types="Cypress"/>
describe('Session edit spec', () => {
    beforeEach(() => {

        cy.login('yoga@studio.com','test!1234');
       
           cy.intercept(
            {
              method: 'GET',
              url: '/api/teacher',
            },
            [  {
                "id": 1,
                "lastName": "DELAHAYE",
                "firstName": "Margot",
                "createdAt": "2024-01-16T11:34:03",
                "updatedAt": "2024-01-16T11:34:03"
            },
            {
                "id": 2,
                "lastName": "THIERCELIN",
                "firstName": "Hélène",
                "createdAt": "2024-01-16T11:34:03",
                "updatedAt": "2024-01-16T11:34:03"
            }]).as('teacher')
           
      })
    it('Edit ', () => {
        cy.intercept(
            {
                method: 'GET',
                url: '/api/session/1',
              },
           [ 
            {
                "id": 1,
                "name": "Basic Asana",
                "date": "2024-01-17T00:00:00.000+00:00",
                "teacher_id": 1,
                "description": "Traditional yoga : 41 Basic Asana, standing and Sitting poses.",
                "users": [],
                "createdAt": "2024-01-16T23:11:25",
                "updatedAt": "2024-01-16T23:11:25"
            }
           ]).as("detail")
        cy.get('#edit').click();
        cy.url().should('include','/update');
       

    })

})
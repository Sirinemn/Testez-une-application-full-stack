///<reference types="Cypress"/>
describe('Session detail spec', () => {
    beforeEach(() => {

        cy.login('yoga@studio.com','test!1234');
      
      })
    it('Detail ', () => {
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
        cy.get('#detail').click();
        cy.contains("Delete");       
    })

})
///<reference types="Cypress"/>
describe('Account spec', () => {
    it('User information', () => {
        cy.login('yoga@studio.com','test!1234');
        cy.intercept(
            {
              method: 'GET',
              url: '/api/user/1',
            },
            [ {
                "id": "1",
                "email": "yoga@studio.com",
                "lastName": "Admin",
                "firstName": "Admin",
                "admin": true,
                "createdAt": "2024-01-16T11:34:03",
                "updatedAt": "2024-01-16T11:34:03"
            } ]).as('user')
        cy.get("#account").click();
        cy.contains("User information")
    })
})
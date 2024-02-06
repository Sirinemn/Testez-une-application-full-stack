///<reference types="Cypress"/>
describe('Logout spec', () => {
    it('Logout successfull', () => {
        cy.login('yoga@studio.com','test!1234');
        cy.pause();
        cy.get('#logout').click();
        cy.url().should('include','/');

    })
})
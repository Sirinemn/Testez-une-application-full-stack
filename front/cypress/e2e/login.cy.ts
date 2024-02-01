describe('Login spec', () => {
  it('Login successfull', () => {
    cy.login('yoga@studio.com','test!1234');

    cy.url().should('include', '/sessions')
    
    cy.pause();

    })

  it('Login failed', () => {
    cy.visit('/login')


    cy.get('input[formControlName=email]').type(`${"yoga@studio.com"}{enter}{enter}`)

    cy.contains('An error occurred')

  })
});

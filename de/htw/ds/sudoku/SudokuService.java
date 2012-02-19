package de.htw.ds.sudoku;

import java.util.Collection;
import java.util.Set;
import javax.jws.WebParam;
import javax.jws.WebService;
import de.htw.ds.TypeMetadata;


/**
 * <p>Shop SOAP service interface.</p>
 */
@WebService
@TypeMetadata(copyright = "2010-2011 Sascha Baumeister, all rights reserved", version = "0.1.0", authors = "Sascha Baumeister")
public interface SudokuService {

	/**
	 * Returns all article data.
	 * @param alias the customer alias
	 * @param password the customer password
	 * @throws NullPointerException if one of the given values is null
	 * @throws IllegalStateException if the login data is invalid
	 * @throws JdbcException if there is a problem with the underlying JDBC connection
	 */
	Set<Article> queryArticles(
		@WebParam(name="alias") String alias,
		@WebParam(name="password") String password
	) throws NullPointerException, IllegalStateException, JdbcException;


	/**
	 * Returns the article data for the given identity.
	 * @param alias the customer alias
	 * @param password the customer password
	 * @param articleIdentity the article identity
	 * @throws NullPointerException if one of the given values is null
	 * @throws IllegalStateException if the login data is invalid, or if there is no article with the given identity
	 * @throws JdbcException if there is a problem with the underlying JDBC connection
	 */
	Article queryArticle(
		@WebParam(name="alias") String alias,
		@WebParam(name="password") String password,
		@WebParam(name="articleIdentity") long articleIdentity
	) throws NullPointerException, IllegalStateException, JdbcException;


	/**
	 * Registers a new customer and returns it's identity. The given customer's identity
	 * is ignored during processing.
	 * @param customer the customer
	 * @return the customer identity
	 * @throws NullPointerException if one of the given values is null, or if the customer
	 *    contains a null alias or null password
	 * @throws IllegalArgumentException if the customer's alias or password are shorter than four digits
	 * @throws IllegalStateException if the insert is unsuccessful
	 * @throws JdbcException if there is a problem with the underlying JDBC connection
	 */
	long registerCustomer(
		@WebParam(name="customer") Customer customer
	) throws NullPointerException, IllegalArgumentException, IllegalStateException, JdbcException;


	/**
	 * Unregisters a customer that has no purchases.
	 * @param alias the customer alias
	 * @param password the customer password
	 * @return the customer identity
	 * @throws NullPointerException if one of the given values is null
	 * @throws IllegalStateException if the login data is invalid, or the customer has any purchases
	 * @throws jdbcException if there is a problem with the underlying JDBC connection
	 */
	long unregisterCustomer(
		@WebParam(name="alias") String alias,
		@WebParam(name="password") String password
	) throws NullPointerException, IllegalStateException, JdbcException;


	/**
	 * Returns the customer data.
	 * @param alias the customer alias
	 * @param password the customer password
	 * @throws NullPointerException if one of the given values is null
	 * @throws IllegalStateException if the login data is invalid
	 * @throws JdbcException if there is a problem with the underlying JDBC connection
	 */
	Customer queryCustomer(
		@WebParam(name="alias") String alias,
		@WebParam(name="password") String password
	) throws NullPointerException, IllegalStateException, JdbcException;


	/**
	 * Creates a purchase from the given items. Note that the suggested price for each item must be
	 * equal to or exceed the current article price. Also, note that orders which exhaust the
	 * available article capacity are rejected.
	 * @param alias the customer alias
	 * @param password the customer password
	 * @param items the purchase items
	 * @return the purchase identity
	 * @throws NullPointerException if one of the given values is null
	 * @throws IllegalArgumentException if one of the given purchase items is priced to low
	 * @throws IllegalStateException if the login data is invalid, or the purchase creation fails
	 * @throws JdbcException if there is a problem with the underlying JDBC connection
	 */
	long createPurchase (
		@WebParam(name="alias") String alias,
		@WebParam(name="password") String password,
		@WebParam(name="purchaseItems") Collection<PurchaseItem> purchaseItems
	) throws NullPointerException, IllegalArgumentException, IllegalStateException, JdbcException;


	/**
	 * Cancels a purchase. Note that cancel requests for purchases will be rejected if they are older
	 * than one hour, or don't target the given customer
	 * @param alias the customer alias
	 * @param password the customer password
	 * @param purchaseIdentity the purchase identity
	 * @throws NullPointerException if one of the given values is null
	 * @throws IllegalStateException if the login data is invalid, if the purchase is too old, or if it is not targeting the given customer
	 * @throws SQLException if there is a problem with the underlying JDBC connection
	 */
	void cancelPurchase (
		@WebParam(name="alias") String alias,
		@WebParam(name="password") String password,
		@WebParam(name="purchaseIdentity") long purchaseIdentity
	) throws NullPointerException, IllegalStateException, JdbcException;


	/**
	 * Queries the given customer's purchases.
	 * @param alias the customer alias
	 * @param password the customer password
	 * @return the customer's purchases
	 * @throws NullPointerException if one of the given values is null
	 * @throws IllegalStateException if the login data is invalid
	 * @throws JdbcException if there is a problem with the underlying JDBC connection
	 */
	Set<Purchase> queryPurchases (
		@WebParam(name="alias") String alias,
		@WebParam(name="password") String password
	) throws NullPointerException, IllegalStateException, JdbcException;


	/**
	 * Queries the given purchase.
	 * @param alias the customer alias
	 * @param password the customer password
	 * @param purchaseIdentity the purchase identity
	 * @return the customer's purchase
	 * @throws NullPointerException if one of the given values is null
	 * @throws IllegalStateException if the login data is invalid, or the purchase doesn't target the customer
	 * @throws JdbcException if there is a problem with the underlying JDBC connection
	 */
	Purchase queryPurchase (
		@WebParam(name="alias") String alias,
		@WebParam(name="password") String password,
		@WebParam(name="purchaseIdentity") long purchaseIdentity
	) throws NullPointerException, IllegalStateException, JdbcException;
}
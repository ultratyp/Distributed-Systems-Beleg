package de.htw.ds.sudoku;

import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import de.htw.ds.Namespaces;
import de.htw.ds.TypeMetadata;


/**
 * <p>Shop client class calling one service method over Java-RMI before terminating.</p>
 */
@TypeMetadata(copyright = "2010-2011 Sascha Baumeister, all rights reserved", version = "0.1.0", authors = "Sascha Baumeister")
public final class SudokuClient {
	private static enum Mode {
		QUERY_ARTICLES,
		QUERY_ARTICLE,
		REGISTER_CUSTOMER,
		UNREGISTER_CUSTOMER,
		QUERY_CUSTOMER,
		CREATE_PURCHASE,
		CANCEL_PURCHASE,
		QUERY_PURCHASE,
		QUERY_PURCHASES
	}

	/**
	 * Application entry point. The given runtime parameters must be a service URI, the
	 * mode, and subsequently the method parameters.
	 * @param args the given runtime arguments
	 * @throws Exception if there is a problem
	 */
	public static void main(final String[] args) throws Exception {
		final URI serviceURI = new URI(args[0]);
		final Mode mode = Mode.valueOf(args[1]);
		final SudokuService proxy = Namespaces.createDynamicSoapServiceProxy(SudokuService.class, serviceURI);

		switch(mode) {
			case QUERY_ARTICLES: {
				final String alias = args[2];
				final String password = args[3];
				final Collection<Article> articles = proxy.queryArticles(alias, password);
				System.out.println("List of articles:");
				for (final Article article : articles) {
					System.out.println("article ID=" + article.getIdentity() + ", " + "description=" + article.getDescription() + ", available units=" + article.getUnits() + ", unit price=" + 0.01 * article.getUnitPrice() + "€.");
				}
				break;
			}
			case QUERY_ARTICLE: {
				final String alias = args[2];
				final String password = args[3];
				final long articleIdentity = Long.parseLong(args[4]);
				final Article article = proxy.queryArticle(alias, password, articleIdentity);
				System.out.println("Article " + articleIdentity + ":");
				System.out.println("description=" + article.getDescription() + ", available units=" + article.getUnits() + ", unit price=" + 0.01 * article.getUnitPrice() + "€.");
				break;
			}
			case REGISTER_CUSTOMER: {
				final Customer customer = new Customer();
				customer.setAlias(args[2]);
				customer.setPassword(args[3]);
				customer.setFirstName(args[4]);
				customer.setLastName(args[5]);
				customer.setStreet(args[6]);
				customer.setPostcode(args[7]);
				customer.setCity(args[8]);
				customer.setEmail(args[9]);
				customer.setPhone(args[10]);
				final long customerIdentity = proxy.registerCustomer(customer);
				System.out.println("Customer created, identity=" + customerIdentity + ".");
				break;
			}
			case UNREGISTER_CUSTOMER: {
				final String alias = args[2];
				final String password = args[3];
				final long customerIdentity = proxy.unregisterCustomer(alias, password);
				System.out.println("Customer removed, identity=" + customerIdentity + ".");
				break;
			}
			case QUERY_CUSTOMER: {
				final String alias = args[2];
				final String password = args[3];
				final Customer customer = proxy.queryCustomer(alias, password);
				System.out.println("Customer " + customer.getIdentity() + ":");
				System.out.println("first name=" + customer.getFirstName() + ", last name=" + customer.getLastName() + ", street=" + customer.getStreet() + ", post-code=" + customer.getPostcode() + ", city=" + customer.getCity() + ", eMail=" + customer.getEmail() + ", phone=" + customer.getPhone() + ".");
				break;
			}
			case CREATE_PURCHASE: {
				final String alias = args[2];
				final String password = args[3];
				final Set<PurchaseItem> purchaseItems = new HashSet<PurchaseItem>();
				for (int index = 4; index < args.length; ++index) {
					final String[] data = args[index].split(",");
					final PurchaseItem item = new PurchaseItem();
					item.setArticleIdentity(Long.parseLong(data[0]));
					item.setUnitPrice(Math.round(100 * Double.parseDouble(data[1])));
					item.setUnits(Integer.parseInt(data[2]));
					purchaseItems.add(item);
				}
				final long purchaseIdentity = proxy.createPurchase(alias, password, purchaseItems);
				System.out.println("Purchase created, identity=" + purchaseIdentity + ".");
				break;
			}
			case CANCEL_PURCHASE: {
				final String alias = args[2];
				final String password = args[3];
				final long purchaseIdentity = Long.parseLong(args[4]);
				proxy.cancelPurchase(alias, password, purchaseIdentity);
				System.out.println("Purchase canceled, identity=" + purchaseIdentity + ".");
				break;
			}
			case QUERY_PURCHASE: {
				final String alias = args[2];
				final String password = args[3];
				final long purchaseIdentity = Long.parseLong(args[4]);
				final Purchase purchase = proxy.queryPurchase(alias, password, purchaseIdentity);
				System.out.println("Purchase " + purchaseIdentity + ":");
				System.out.println("creation=" + new Date(purchase.getCreationTimestamp()) + ", taxRate=" + 100 * purchase.getTaxRate() + "%, gross=" + 0.01 * purchase.getGrossPrice() + "€, net=" + 0.01 * purchase.getNetPrice() + "€.");
				for (final PurchaseItem item : purchase.getItems()) {
					System.out.println("article ID=" + item.getArticleIdentity() + ", unit gross price=" + 0.01 * item.getUnitPrice() + "€, units=" + item.getUnits() + ".");
				}
				break;
			}
			case QUERY_PURCHASES: {
				final String alias = args[2];
				final String password = args[3];
				final Collection<Purchase> purchases = proxy.queryPurchases(alias, password);
				for (final Purchase purchase : purchases) {
					System.out.println("Purchase " + purchase.getIdentity() + ":");
					System.out.println("creation=" + new Date(purchase.getCreationTimestamp()) + ", taxRate=" + 100 * purchase.getTaxRate() + "%, gross=" + 0.01 * purchase.getGrossPrice() + "€, net=" + 0.01 * purchase.getNetPrice() + "€.");
					for (final PurchaseItem item : purchase.getItems()) {
						System.out.println("article ID=" + item.getArticleIdentity() + ", unit gross price=" + 0.01 * item.getUnitPrice() + "€, units=" + item.getUnits() + ".");
					}
					System.out.println();
				}
				break;
			}
		}
	}
}
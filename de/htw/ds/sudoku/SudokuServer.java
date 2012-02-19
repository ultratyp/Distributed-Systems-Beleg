package de.htw.ds.sudoku;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import javax.jws.WebService;
import javax.sql.DataSource;
import javax.xml.ws.Endpoint;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPBinding;
import de.htw.ds.SocketAddress;
import de.htw.ds.TypeMetadata;


/**
 * <p>Shop server class implementing a JAX-WS based server.
 */
@WebService (endpointInterface="de.htw.ds.shop.SoapShopService", serviceName="SoapShopService", portName="SoapShopPort")
@TypeMetadata(copyright = "2010-2011 Sascha Baumeister, all rights reserved", version = "0.1.0", authors = "Sascha Baumeister")
public class SudokuServer extends AbstractShopService implements SudokuService {

	private final Endpoint endpoint;


	/**
	 * Creates a new shop server that is exported it to the given
	 * SOAP service URI.
	 * @param binding the SOAP binding for the endpoint
	 * @param serviceURI the service URI used for publishing
	 * @param the data source
	 * @param the tax rate
	 * @throws NullPointerException if any of the given arguments is null
	 * @throws IllegalArgumentException if the given tax rate is strictly negative
	 * @throws WebServiceException if the service URI's port is already in use
	 */
	public SudokuServer(final String binding, final URI serviceURI, final DataSource dataSource, final double taxRate) {
		super(dataSource, taxRate);

		this.endpoint = Endpoint.create(binding, this);
		this.endpoint.publish(serviceURI.toASCIIString());
 	}


	/**
	 * Closes the receiver, thereby stopping it's SOAP endpoint.
	 */
	public void close() {
		this.endpoint.stop();
	}


	/**
	 * Application entry point. The given runtime parameters must be a SOAP service port,a
	 * SOAP service name, a JDBC connection URL, a database user-ID, a database password,
	 * and a tax rate.
	 * @param args the given runtime arguments
	 * @throws URISyntaxException if one of the given service URIs is malformed
	 * @throws JdbcException if none of the supported JDBC drivers is installed
	 * @throws WebServiceException if the given port is already in use
	 */
	public static void main(final String[] args) throws URISyntaxException, JdbcException, RemoteException {
		final long timeStamp = System.currentTimeMillis();
		final int servicePort = Integer.parseInt(args[0]);
		final String serviceName = args[1];
		final URI soapServiceURI = new URI("http", null, SocketAddress.getLocalAddress().getCanonicalHostName(), servicePort, "/" + serviceName, null, null);
		final URI jdbcConnectionURI = new URI(args[2]);
		final double taxRate = Double.parseDouble(args[5]);

		final DataSource dataSource = createDataSource(jdbcConnectionURI, args[3], args[4]);
		final SudokuServer server = new SudokuServer(SOAPBinding.SOAP11HTTP_BINDING, soapServiceURI, dataSource, taxRate);
		try {
			System.out.println("JAX-WS based shop server running.");
			System.out.println("Service URI is " + soapServiceURI + ", data source URL is " + args[2] + ", type \"quit\" to stop.");
			System.out.println("Startup time is " + (System.currentTimeMillis() - timeStamp) + "ms.");
			System.out.println("Tax-rate is " + 100 * taxRate + "%.");

			final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			try { while (!"quit".equals(reader.readLine())); } catch (final IOException exception) {}
		} finally {
			server.close();
		}
	}
}
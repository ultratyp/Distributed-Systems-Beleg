package de.htw.ds.sudoku;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;


/**
 * <p>Shop connector class abstracting the use of JDBC for a set of given operations.
 * Note that a connector always works on the same JDBC connection, therefore allowing
 * multiple method calls to operate within a single local transaction, if desired.</p>
 */
public final class SudokuConnector {

	private static final String SQL_INSERT_SUDOKU = "INSERT INTO Sudoku (`hash`, `digitsToSolve`, `digitsSolved`) VALUES (?, ?, ?)";
	private static final String SQL_SELECT_SUDOKU = "SELECT `digitsSolved` FROM Sudoku Sudoku WHERE `hash` = ?";
	
	private final Connection connection;

	/**
	 * Public constructor. 
	 * @param connection a JDBC connection, usually created from a JDBC data source
	 */
	public SudokuConnector(final Connection connection) {
		this.connection = connection;
	}

	/**
	 * Returns the JDBC connection used.
	 * @return the connection
	 */
	public Connection getConnection() {
		return this.connection;
	}


	/**
	 * Stores solution in database.
	
	 * @throws NullPointerException if one of the given values is null
	 * @throws IllegalStateException if the insert is unsuccessful
	 * @throws SQLException if there is a problem with the underlying JDBC connection
	 */
	
	public void storeSolution(byte[] digitsToSolve, byte[] digitsSolved) throws SQLException {
		final PreparedStatement statement = this.connection.prepareStatement(SQL_INSERT_SUDOKU);

		final String digitsToSolveAsString = new String(digitsToSolve);
		final String digitsSolvedAsString = new String(digitsSolved);
		final int hash = digitsToSolveAsString.hashCode();
		statement.setInt(1, hash);
		statement.setString(2, digitsToSolveAsString);
		statement.setString(3, digitsSolvedAsString);
		
		try {
			if(statement.executeUpdate() != 1) throw new IllegalStateException();
		} catch (MySQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
		}
	}
	
	
	public byte[] querySolution(byte[] digitsToSolve) throws SQLException {
		final PreparedStatement statement = this.connection.prepareStatement(SQL_SELECT_SUDOKU);
		
		final String stringToHash = new String(digitsToSolve);
		final int hash = stringToHash.hashCode();
		statement.setInt(1, hash);
		
		final ResultSet resultSet = statement.executeQuery();
	
		if (resultSet.next()) {
			return resultSet.getString("digitsSolved").getBytes();
		}
		return new byte[0];
	}

	
	public boolean solutionExists(byte[] digitsToSolve) throws SQLException {
		
		final PreparedStatement statement = this.connection.prepareStatement(SQL_SELECT_SUDOKU);

		final String digitsToSolveAsString = new String(digitsToSolve);
		final int hash = digitsToSolveAsString.hashCode();
		statement.setInt(1, hash);
		
		final ResultSet resultSet = statement.executeQuery();
		return (resultSet.next());
	}

	
}

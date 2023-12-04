package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Represents the model for the expense tracker application.
 * Manages the list of transactions and provides methods for interacting with the data.
 * Implements the Observer design pattern where it acts as the Observable class.
 */
public class ExpenseTrackerModel {

  //encapsulation - data integrity
  private List<Transaction> transactions;
  private List<Integer> matchedFilterIndices;

  // This is applying the Observer design pattern.                          
  // Specifically, this is the Observable class. 
    /**
     * Constructs a new ExpenseTrackerModel with an empty list of transactions.
     */
  public ExpenseTrackerModel() {
    transactions = new ArrayList<Transaction>();
    matchedFilterIndices = new ArrayList<Integer>();
  }

/**
     * Adds a new transaction to the list of transactions.
     */
  public void addTransaction(Transaction t) {
    // Perform input validation to guarantee that all transactions added are non-null.
    if (t == null) {
      throw new IllegalArgumentException("The new transaction must be non-null.");
    }
    transactions.add(t);
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();

    this.stateChanged();
  }
    /**
     * Removes a transaction from the list of transactions.
     */
  public void removeTransaction(Transaction t) {
    transactions.remove(t);
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();

    this.stateChanged();
  }
    /**
     * Returns an unmodifiable view of the list of transactions.
     */
  public List<Transaction> getTransactions() {
    //encapsulation - data integrity
    return Collections.unmodifiableList(new ArrayList<>(transactions));
  }

    /**
     * Sets the matched filter indices for the model.
     */
  public void setMatchedFilterIndices(List<Integer> newMatchedFilterIndices) {
      // Perform input validation
      if (newMatchedFilterIndices == null) {
	  throw new IllegalArgumentException("The matched filter indices list must be non-null.");
      }
      for (Integer matchedFilterIndex : newMatchedFilterIndices) {
	  if ((matchedFilterIndex < 0) || (matchedFilterIndex > this.transactions.size() - 1)) {
	      throw new IllegalArgumentException("Each matched filter index must be between 0 (inclusive) and the number of transactions (exclusive).");
	  }
      }
      // For encapsulation, copy in the input list 
      this.matchedFilterIndices.clear();
      this.matchedFilterIndices.addAll(newMatchedFilterIndices);

      this.stateChanged();
  }
    /**
     * Returns a copy of the current list of matched filter indices.
     */
  public List<Integer> getMatchedFilterIndices() {
      // For encapsulation, copy out the output list
      List<Integer> copyOfMatchedFilterIndices = new ArrayList<Integer>();
      copyOfMatchedFilterIndices.addAll(this.matchedFilterIndices);
      return copyOfMatchedFilterIndices;
  }

  private List<ExpenseTrackerModelListener> listeners = new ArrayList<>();

  /**
   * Registers the given ExpenseTrackerModelListener for
   * state change events.
   *
   * @param listener The ExpenseTrackerModelListener to be registered
   * @return If the listener is non-null and not already registered,
   *         returns true. If not, returns false.
   */   
  public boolean register(ExpenseTrackerModelListener listener) {
      // For the Observable class, this is one of the methods.
      //
      if (listener != null && !listeners.contains(listener)) {
        listeners.add(listener);
        return true;
    }
      return false;
  }

    /**
     * Returns the number of registered listeners.
     */
  public int numberOfListeners() {
      // For testing, this is one of the methods.
      //
      return listeners.size();
      //return 0;
  }

    /**
     * Checks if the given listener is registered.
     */
  public boolean containsListener(ExpenseTrackerModelListener listener) {
      // For testing, this is one of the methods.
      //
      return listeners.contains(listener);
      //return false;
  }

    /**
     * Notifies all registered listeners that the state of the model has changed.
     * Invokes the `update` method on each listener.
     */
  protected void stateChanged() {
      // For the Observable class, this is one of the methods.
      //
      for (ExpenseTrackerModelListener listener : listeners) {
        listener.update(this);
    }
  }
}

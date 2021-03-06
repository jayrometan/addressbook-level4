package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.purchase.Purchase;
import seedu.address.model.purchase.PurchaseList;

/**
 * Wraps all data at the expenditure list level
 */


public class ExpenditureList implements ReadOnlyExpenditureList {

    private final PurchaseList purchases;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();
    {
        purchases = new PurchaseList();

    }

    public ExpenditureList() {}

    /**
     * Creates an ExpenditureList using the Purchases in the {@code toBeCopied}
     */
    public ExpenditureList(ReadOnlyExpenditureList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the purchase list with {@code purchases}.
     */
    public void setPurchases(List<Purchase> purchases) {
        this.purchases.setPurchases(purchases);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code ExpenditureList} with {@code newData}.
     */
    public void resetData(ReadOnlyExpenditureList newData) {
        requireNonNull(newData);

        setPurchases(newData.getPurchaseList());
    }

    //// purchase-level operations

    /**
     * Adds a purchase to the contact list.
     */
    public void addPurchase(Purchase p) {
        purchases.add(p);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code ExpenditureList}.
     * {@code key} must exist in the expenditure list.
     */
    public void removePurchase(Purchase key) {
        purchases.remove(key);
        indicateModified();
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the expenditure list has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return purchases.asUnmodifiableObservableList().size() + " purchases";
        // TODO: refine later
    }

    @Override
    public ObservableList<Purchase> getPurchaseList() {
        return purchases.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpenditureList // instanceof handles nulls
                && purchases.equals(((ExpenditureList) other).purchases));
    }

    @Override
    public int hashCode() {
        return purchases.hashCode();
    }

}

package classes;

import java.util.*;
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FrequentItem {
	public FrequentItem() {
		this._item = new LinkedHashSet();
	}
	
	private FrequentItem(FrequentItem _genItemSet) {
		this._item = new LinkedHashSet(_genItemSet._item);
	}

	public void _addToItemSet(Apriori item) {
		_item.add(item);
	}
	
	public FrequentItem _divide(FrequentItem _clone) 
	{
		FrequentItem _freq = new FrequentItem(this);
		_freq._item.retainAll(_clone._item);
		return _freq;
	}

	public FrequentItem _combination(FrequentItem _clone)
	{
		FrequentItem _freq = new FrequentItem(this);
		_freq._item.addAll(_clone._item);
		return _freq;
	}
	public FrequentItem _remove(FrequentItem _clone) 
	{
		FrequentItem _freq = new FrequentItem(this);
		_freq._item.removeAll(_clone._item);
		return _freq;
	}
	private void createNewItemsets( FrequentItem frItemSet,LinkedList itemList,LinkedHashSet newSet, int match) {
		boolean itemAdded = false;
		frItemSet = new FrequentItem(frItemSet);
		while (true) {
			if (match == itemList.size() - 1) {
				if (frItemSet.size() != 0
						&& frItemSet.size() != itemList.size()) {
					newSet.add(frItemSet);
				}
			} else {
				createNewItemsets(frItemSet,itemList,
						newSet, match+1 );
			}
			if (itemAdded) {
				break;
			} else {
				frItemSet = new FrequentItem(frItemSet);
				frItemSet._addToItemSet((Apriori) itemList.get(match));
				itemAdded = true;
			}
		}
	}
	public Set createNewItemsets() {
		LinkedHashSet newSet = new LinkedHashSet();
		createNewItemsets(new FrequentItem(),new LinkedList(_item), newSet, 0
	);
		return newSet;
	}

	public int size() {
		return _item.size();
	}

	public Iterator getItemIterator() {
		return _item.iterator();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer out = new StringBuffer();
		Iterator iterator = _item.iterator();
		while (iterator.hasNext()) {
			Apriori item = (Apriori) iterator.next();
			out.append(item.toStringto());
			if (iterator.hasNext()) {
				out.append(" ");
			}
		}
		return out.toString();
	}
	@Override
	public boolean equals(Object _nextItem) {
		// TODO Auto-generated method stub
		return ((FrequentItem) _nextItem)._item.equals(_item);
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return _item.hashCode();
	}
	protected LinkedHashSet _item;
	protected Object name;
}

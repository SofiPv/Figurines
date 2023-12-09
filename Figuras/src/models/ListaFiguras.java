package models;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.AbstractListModel;

public class ListaFiguras extends AbstractListModel<ModeloFigura> implements Iterable<ModeloFigura> {

	private final ArrayList<ModeloFigura> data;

	public ListaFiguras() {
		super();
		data = new ArrayList<>();
	}

	public void checkForIncompletes() {

		for (int i = 0; i < getSize(); i++) {
			if (!getElementAt(i).canDraw()) {

				removeElement(getElementAt(i));

			}
		}

	}

	public void unselectAll() {

		for (var model : this) {
			model.setSelected(false);
		}

	}

	public boolean addElement(ModeloFigura figura) {

		int index = indexOf(figura);

		if (index != -1) {
			setElementAt(index, figura);
			return true;
		}

		index = getSize();
		boolean completed = data.add(figura);

		if (completed) {
			fireIntervalAdded(this, index, index);
		}

		return completed;
	}

	public boolean removeElement(ModeloFigura figura) {

		int index = indexOf(figura);

		boolean completed = data.remove(figura);

		if (index != -1) {
			fireIntervalRemoved(this, index, index);
		}

		return completed;
	}

	public ModeloFigura setElementAt(int index, ModeloFigura figura) {

		ModeloFigura old = data.set(index, figura);
		fireContentsChanged(this, index, index);

		return old;
	}

	public ModeloFigura removeElementAt(int index) {

		ModeloFigura old = data.remove(index);
		fireIntervalRemoved(this, index, index);

		return old;
	}

	public void clear() {

		int endIndex = getSize() - 1;
		data.clear();

		if (endIndex != -1) {
			fireIntervalRemoved(this, 0, endIndex);
		}

	}

	public boolean isEmpty() {
		return data.isEmpty();
	}

	public boolean contains(ModeloFigura figura) {

		for (ModeloFigura it : this) {

			if (it.getId() == figura.getId()) {
				return true;
			}

		}

		return false;
	}

	public int indexOf(ModeloFigura figura) {
		if (!contains(figura)) {
			return -1;
		}

		for (int i = 0; i < data.size(); i++) {

			if (data.get(i).getId() == figura.getId()) {
				return i;
			}

		}

		return -1;
	}

	@Override
	public int getSize() {
		return data.size();
	}

	@Override
	public ModeloFigura getElementAt(int index) {
		return data.get(index);
	}

	@Override
	public Iterator<ModeloFigura> iterator() {
		return data.iterator();
	}

	public ModeloFigura[] toArray() {
		return (ModeloFigura[]) data.toArray();
	}

	public ListaFiguras sort() {

		ListaFiguras other = new ListaFiguras();

		// Buscar por los no seleccionados e ingresarlos al inicio para dibujarlos primero
		for (var it : this) {

			if (!it.isSelected()) {
				other.addElement(it);
			}

		}

		// Luego añadir los seleccionados para que aparezcan siempre arriba de los demás
		for (var it : this) {

			if (it.isSelected()) {
				other.addElement(it);
			}

		}

		return other;
	}
}

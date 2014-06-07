package ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import qubject.MediaInterface;
import qubject.Qubject;
import qubject.QubjectModifierInterface;

public class QubjectComboBoxRenderer extends JLabel implements ListCellRenderer {

	public QubjectComboBoxRenderer() {
		setOpaque(true);
		setHorizontalAlignment(CENTER);
		setVerticalAlignment(CENTER);
	}

	/*
	 * This method finds the image and text corresponding
	 * to the selected value and returns the label, set up
	 * to display the text and image.
	 */
	public Component getListCellRendererComponent(
			JList list,
			Object value,
			int index,
			boolean isSelected,
			boolean cellHasFocus) {

		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		//Cast to QubjectModifierInterface
		setText(((MediaInterface)value).getName());
		setFont(list.getFont());
		
		return this;
	}
}
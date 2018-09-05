package org.mastodon.revised.ui.selection.creator.parser.plugin.settings;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class SelectionCreatorSettingsPanel extends JPanel
{

	private static final long serialVersionUID = 1L;

	private final SelectionCreatorSettings settings;

	private final JTextPane textPaneDescription;

	private final JTextPane textPaneExpression;

	public SelectionCreatorSettingsPanel( final SelectionCreatorSettings settings )
	{
		this.settings = settings;

		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		setLayout( gridBagLayout );

		final JLabel lblExpression = new JLabel( "Expression:" );
		lblExpression.setFont( lblExpression.getFont().deriveFont( lblExpression.getFont().getStyle() | Font.BOLD ) );
		final GridBagConstraints gbc_lblExpression = new GridBagConstraints();
		gbc_lblExpression.anchor = GridBagConstraints.WEST;
		gbc_lblExpression.insets = new Insets(5, 5, 5, 5);
		gbc_lblExpression.gridx = 0;
		gbc_lblExpression.gridy = 0;
		add( lblExpression, gbc_lblExpression );

		final JButton btnHelp = new JButton( "Help" );
		final GridBagConstraints gbc_btnHelp = new GridBagConstraints();
		gbc_btnHelp.insets = new Insets( 0, 0, 5, 0 );
		gbc_btnHelp.gridx = 1;
		gbc_btnHelp.gridy = 0;
		add( btnHelp, gbc_btnHelp );

		this.textPaneExpression = new JTextPane();
		textPaneExpression.setBorder( BorderFactory.createCompoundBorder(
				textPaneExpression.getBorder(),
				BorderFactory.createEmptyBorder( 15, 15, 15, 15 ) ) );
		textPaneExpression.setFont( new Font(
				"Monospaced",
				textPaneExpression.getFont().getStyle(),
				textPaneExpression.getFont().getSize() + 2 ) );
		textPaneExpression.setText( settings.expression() );
		tabTransferFocus( textPaneExpression );
		final GridBagConstraints gbc_textAreaExpression = new GridBagConstraints();
		gbc_textAreaExpression.gridwidth = 2;
		gbc_textAreaExpression.insets = new Insets(15, 15, 15, 15);
		gbc_textAreaExpression.fill = GridBagConstraints.BOTH;
		gbc_textAreaExpression.gridx = 0;
		gbc_textAreaExpression.gridy = 1;
		add( textPaneExpression, gbc_textAreaExpression );

		final JLabel lblDescription = new JLabel( "Description:" );
		lblDescription.setFont( lblDescription.getFont().deriveFont( lblDescription.getFont().getStyle() | Font.BOLD ) );
		final GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.insets = new Insets(5, 5, 5, 5);
		gbc_lblDescription.anchor = GridBagConstraints.WEST;
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 2;
		add( lblDescription, gbc_lblDescription );

		this.textPaneDescription = new JTextPane();
		textPaneDescription.setOpaque( false );
		textPaneDescription.setEditable( true );
		tabTransferFocus( textPaneDescription );
		final GridBagConstraints gbc_editorPaneDescription = new GridBagConstraints();
		gbc_editorPaneDescription.insets = new Insets(15, 15, 15, 15);
		gbc_editorPaneDescription.gridwidth = 2;
		gbc_editorPaneDescription.fill = GridBagConstraints.BOTH;
		gbc_editorPaneDescription.gridx = 0;
		gbc_editorPaneDescription.gridy = 3;
		add( textPaneDescription, gbc_editorPaneDescription );

		final JSeparator separator = new JSeparator();
		final GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets( 0, 0, 5, 0 );
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.gridwidth = 2;
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 4;
		add( separator, gbc_separator );

		final JTextArea textAreaMessage = new JTextArea();
		textAreaMessage.setOpaque( false );
		textAreaMessage.setEditable( false );
		final GridBagConstraints gbc_textAreaMessage = new GridBagConstraints();
		gbc_textAreaMessage.insets = new Insets( 0, 0, 0, 5 );
		gbc_textAreaMessage.fill = GridBagConstraints.BOTH;
		gbc_textAreaMessage.gridx = 0;
		gbc_textAreaMessage.gridy = 5;
		add( textAreaMessage, gbc_textAreaMessage );

		final JButton btnRun = new JButton( "Run" );
		final GridBagConstraints gbc_btnRun = new GridBagConstraints();
		gbc_btnRun.gridx = 1;
		gbc_btnRun.gridy = 5;
		add( btnRun, gbc_btnRun );

		setPreferredSize( new Dimension( 400, 200 ) );

		/*
		 * Update display on settings changes.
		 */

		settings.updateListeners().add( () -> updateFromSettings() );
		final MyFocusListener focusListener = new MyFocusListener();
		textPaneExpression.addFocusListener( focusListener );
		textPaneDescription.addFocusListener( focusListener );
		updateFromSettings();
	}

	private void commitToSettings()
	{
		settings.setExpression( textPaneExpression.getText() );
		settings.setDescription( textPaneDescription.getText() );
	}

	private void updateFromSettings()
	{
		textPaneExpression.setText( settings.expression() );
		textPaneDescription.setText( settings.description() );
		repaint();
	}

	private class MyFocusListener implements FocusListener
	{

		@Override
		public void focusGained( final FocusEvent e )
		{}

		@Override
		public void focusLost( final FocusEvent e )
		{
			commitToSettings();
		}
	}

	private static final void tabTransferFocus( final JComponent component )
	{
		component.setFocusTraversalKeys( KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null );
		component.setFocusTraversalKeys( KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null );
	}

}

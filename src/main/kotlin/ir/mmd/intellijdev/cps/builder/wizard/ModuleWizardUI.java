package ir.mmd.intellijdev.cps.builder.wizard;

import ir.mmd.intellijdev.cps.xml.Directory;
import ir.mmd.intellijdev.cps.xml.RunAt;
import ir.mmd.intellijdev.cps.xml.Starter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.util.Map;

public class ModuleWizardUI {
	private final Map<String, Starter> starterModel;
	
	private JPanel component;
	private JTree projectStructure;
	private JComboBox<String> starterType;
	private JTextArea scriptArea;
	
	public ModuleWizardUI(@NotNull Map<String, Starter> starterModel) {
		this.starterModel = starterModel;
		starterType.addItem("");
		starterModel.keySet().forEach(starterType::addItem);
		
		freshenUI();
		setupListeners();
	}
	
	private void freshenUI() {
		projectStructure.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("<root>")));
		scriptArea.setText("");
	}
	
	private void setupListeners() {
		starterType.addActionListener(e -> {
			String item = (String) starterType.getSelectedItem();
			Starter starter = starterModel.get(item);
			
			if (starter == null) {
				freshenUI();
				return;
			}
			
			if (starter.scripts != null) {
				StringBuilder builder = new StringBuilder();
				starter.scripts.forEach(script -> builder
					.append(script.runAt == RunAt.Beginning ? "<beginning> " : "<end> ")
					.append(script.content)
					.append("\n")
				);
				scriptArea.setText(builder.toString());
			}
			
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("<root>");
			
			if (starter.structure.directories != null) {
				starter.structure.directories.forEach(directory -> root.add(resolveTree(directory)));
			}
			
			if (starter.structure.files != null) {
				starter.structure.files.forEach(file -> root.add(new DefaultMutableTreeNode(file.name)));
			}
			
			projectStructure.setModel(new DefaultTreeModel(root));
			
			for (int i = 0; i < projectStructure.getRowCount(); i++) {
				projectStructure.expandRow(i);
			}
		});
	}
	
	private @NotNull DefaultMutableTreeNode resolveTree(@NotNull Directory directory) {
		DefaultMutableTreeNode tree = new DefaultMutableTreeNode(directory.name);
		
		if (directory.directories != null) {
			directory.directories.forEach(childDirectory -> tree.add(resolveTree(childDirectory)));
		}
		
		if (directory.files != null) {
			directory.files.forEach(file -> tree.add(new DefaultMutableTreeNode(file.name)));
		}
		
		if (tree.getChildCount() == 0) {
			tree.add(new DefaultMutableTreeNode("<empty>"));
		}
		
		return tree;
	}
	
	public @NotNull JPanel getComponent() {
		return component;
	}
	
	public @Nullable Starter getStarter() {
		return starterModel.get((String) starterType.getSelectedItem());
	}
}

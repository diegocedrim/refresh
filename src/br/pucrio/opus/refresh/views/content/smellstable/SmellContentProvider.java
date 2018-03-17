package br.pucrio.opus.refresh.views.content.smellstable;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import br.pucrio.opus.organic.collector.Smell;
import br.pucrio.opus.organic.metrics.MetricValue;

public class SmellContentProvider implements ITreeContentProvider {
    @Override
    public boolean hasChildren(Object element) {
        return element instanceof Smell;
    }

    @Override
    public Object getParent(Object element) {
    		if (element instanceof Smell) {
    			return null;
    		}  else  {
    			MetricValue value = (MetricValue)element;
    			return value.getSmell();
    		}
    }

    @Override
    public Object[] getElements(Object inputElement) {
        return ArrayContentProvider.getInstance().getElements(inputElement);
    }

    @Override
    public Object[] getChildren(Object parentElement) {
    		if (parentElement instanceof Smell) {
    			Smell smell = (Smell)parentElement;
			return smell.getAllMetricValues().toArray();
		}  else  {
			return null;
		}
    }
}

package CA;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Transition of a contract automaton
 * 
 * @author Davide Basile
 *
 */
public class CATransition { 
	final private CAState source;
	final private CAState target;
	final private CALabel label;

	public CATransition(CAState source, CALabel label, CAState target){
		if (source==null || label==null || target==null)
			throw new IllegalArgumentException("source, label or target null");
		this.source=source;
		this.target=target;
		this.label=label;
	}

	public CATransition(CAState source, boolean offererMinorRequester, String offeraction, CAState target)
	{
		if (source==null||target==null||offeraction==null)
			throw new IllegalArgumentException("Bug: trying to instantiate a CATransition with some null value");
		this.source=source;
		this.target=target;
		
		int[] principals= IntStream.range(0,source.getState().length)
		.filter(i->source.getState()[i]!=target.getState()[i])
		.toArray();
		if (principals.length!=2)
			throw new IllegalArgumentException("Bug: only two principals are allowed to move");//FIXME loop not allowed

		if (offererMinorRequester)
			this.label=new CALabel(source.getRank(),principals[0],principals[1],offeraction);
		else
			this.label=new CALabel(source.getRank(),principals[1],principals[0],offeraction);
	}


	
	public CATransition(CAState source, String action, CAState target)
	{
		if (source==null||target==null||action==null)
			throw new IllegalArgumentException("Bug: trying to instantiate a CATransition with some null value");
		this.source=source;
		this.target=target;
		
		int[] principal= IntStream.range(0,source.getState().length)
		.filter(i->source.getState()[i]!=target.getState()[i])
		.toArray();
		if (principal.length!=1)
			throw new IllegalArgumentException("Bug: only one principal is allowed to move");//FIXME loop not allowed
		
		this.label=new CALabel(source.getRank(),principal[0],action); 
	}

	/**
	 * @return		the source state of the transition
	 */
	public CAState getSource()
	{
		return this.source;
	}

	/**
	 * @return		the target state of the transition
	 */
	public CAState getTarget()
	{
		return target;
	}

	public List<String> getLabelAsList()
	{
		return label.getLabelAsList();
	}

	public CALabel getLabel()
	{
		return label;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CATransition other = (CATransition) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}

}
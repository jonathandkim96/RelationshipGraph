package apps;

import structures.Queue;
import structures.Stack;

import java.util.*;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null if there is no
	 *         path from p1 to p2
	 */
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {
		
		/** COMPLETE THIS METHOD **/
		
		//look at names, check neighbor
		
		if (g.map.containsKey(p1)==false || g.map.containsKey(p2)==false) {
			return null;
		}
		int numMembers = g.members.length;
		Queue<Integer> queue = new Queue<Integer>();
		boolean[] visited = new boolean[numMembers];
		int[] previous = new int[numMembers];
		ArrayList<String> chain = new ArrayList<String>();
		if (p1 == p2) {
			chain.add(p1);
			return chain;
		}
		queue.enqueue(g.map.get(p1));
		//BFS
		while ((queue.isEmpty())==false) {
			int curr = queue.dequeue();
			visited[curr] = true;
			Friend nextFriend = g.members[curr].first;
			for (int i = 0 ; i < numMembers ; i++) {
				int temp = g.map.get(p1);
			}
			while (nextFriend != null) {
				if (visited[nextFriend.fnum] == false) {
					queue.enqueue(nextFriend.fnum);
					if (previous[nextFriend.fnum] == 0) {
						previous[nextFriend.fnum]= curr; 
					}
					if (g.members[nextFriend.fnum].name.equals(p2)) {
						int temp = g.map.get(p2);
						while (previous[temp] != g.map.get(p1)) {
							chain.add(0,g.members[(previous[temp])].name);
							temp = previous[temp];
						}
						chain.add(0,g.members[(previous[temp])].name);
						if (p1 != p2) {
							chain.add(p2);
							return chain;
						}
						
					}
				}
				nextFriend = nextFriend.next;
			}
		}
		if (chain.isEmpty()) {
			return null;
		}else {
			return chain;
		}
	}
	
	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null if there is no student in the
	 *         given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {
		//search. once encounter a different school, return graph
		int numberMembers = g.members.length;

		boolean[] memberInClique = new boolean[numberMembers];
		ArrayList<ArrayList<String>> cliquesList = new ArrayList<ArrayList<String>>();
		for (int i = 0 ; i < numberMembers ; i++) {
			if (memberInClique[i] == false) {
				if ((g.members[i].student == true)) {
					if (g.members[i].school.equals(school)) {

						ArrayList<String> clique = new ArrayList<String>();
						if (clique.contains(g.members[i].name)) {
							//nothing
						}else {
							clique.add(g.members[i].name);
						}
						cliquesList.add(clique);
						Queue<Integer> queue = new Queue<Integer>();
						queue.enqueue(i);
						while (queue.isEmpty()==false) {
							int ctr = queue.dequeue();
							memberInClique[ctr] = true;
							if (clique.contains(g.members[ctr].name)) {
								//nothing
							}
							else {
								clique.add(g.members[ctr].name);
							}
							Friend navigator = g.members[ctr].first;
							Person navPerson = null;
							if (navigator != null) {
								navPerson = g.members[navigator.fnum];
							}else {break;}
							
							while (navigator != null) {
								if (navPerson.student) {
									if (navPerson.school.equals(school)) {
										if (memberInClique[navigator.fnum] == false){
											queue.enqueue(navigator.fnum);
										}
									}
								}
								navigator = navigator.next;
								if (navigator != null) {
									navPerson = g.members[navigator.fnum];
								}
							}
						}
					}
				}
			}
		}
		if (cliquesList.isEmpty()) {
			cliquesList = null;
		}
		
		/** COMPLETE THIS METHOD **/
		
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
		return cliquesList;
		
	}
	
	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {

		/** COMPLETE THIS METHOD **/
		int amountOfMembers = g.members.length;
		ArrayList<String> connectorList = connRec(g , amountOfMembers);
		
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
		return connectorList;
	}
	private static ArrayList<String> connRec(Graph g, int membersAmount){

		/** COMPLETE THIS METHOD **/
		ArrayList<String> connectors = new ArrayList<String>();
		int[] sizes = new int[membersAmount];
		int ctr = 0;
		while (ctr < membersAmount) {
			Friend pointer = g.members[ctr].first;
			int count = 0;
			while (pointer != null) {
				count++;
				pointer = pointer.next;
			}
			sizes[ctr] = count;
			ctr++;
		}
		
		for (int j = 0 ; j < membersAmount ; j++) {
			if (sizes[j] > 1) {
				ArrayList<String> surrMemb = new ArrayList<String>();
				Friend temp = g.members[j].first;
				Friend newtemp = temp;
				
				while (temp != null) {
					surrMemb.add(g.members[temp.fnum].name);
					if (temp != null) {
						temp = temp.next;
					}
				}
				g.members[j].first = null;
				for (int k = 0; k < surrMemb.size(); k++) {
					for(int l = 0; l < surrMemb.size(); l++) {
						if (k != l) {
							String first = surrMemb.get(k);
							String second = surrMemb.get(l);
							boolean containsMember = true;
							if (shortestChain(g, first, second) == null) {
								if (!connectors.contains(g.members[j].name)) {
									containsMember = false;
								}
								if (containsMember == false) {
									connectors.add(g.members[j].name);
									break;
								}
							}
						}	
					}	
					if (!connectors.contains(g.members[j].name)) {
					}
					else {
						break;
					}
				}
				if (newtemp != null) {
					g.members[j].first = newtemp;
				}
			}
		}
		
		if (connectors.isEmpty()) {
			connectors = null;
		}
		return connectors;
	}
}

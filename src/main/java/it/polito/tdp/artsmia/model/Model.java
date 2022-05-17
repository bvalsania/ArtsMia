package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.artsmia.db.Adiacenza;
import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {

	//creazione grafo di oggetti d'arte e di default vertici pesato
	private Graph<ArtObject, DefaultWeightedEdge> grafo;
	private ArtsmiaDAO dao;
	
	//creao una identity map che è una hashmap all'interno del model
	//che si salva id oggetto e oggetto stesso
	//non uso una new e uso empre quelli
	private Map<Integer, ArtObject> idMap;
	
	
	
	public Model() {
		dao = new ArtsmiaDAO();
		idMap = new HashMap<Integer, ArtObject>();
		
	}
	

	public void creaGrafo() {
		
		//creazione grafo pesato, semplice e non orientato
			grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
			
		//aggiungere vertici
		//1.recupero tutti gl iartobject dal db
		//2.li inserisco come vertici
		
			//List <ArtObject> vertici = dao.listObjects();
			dao.ListObjects(idMap);
			Graphs.addAllVertices(grafo, idMap.values());
			
			//aggiungi gli archi
			
			/*	//APPROCCIO1
				// doppio ciclo for sui vertici
				// dati due vertici, controllo se sono collegati
			
			for(ArtObject a1: this.grafo.vertexSet()) {
				for(ArtObject a2: this.grafo.vertexSet()) {
					if(!a1.equals(a2) && !this.grafo.containsEdge(a1,a2)) {
						//devo collegare a1 ad a2
						int peso= dao.getPeso(a1,a2);
						if(peso>0) {
							Graphs.addEdge(this.grafo, a1, a2, peso);
						}
						
						
						
					}
				}
			}
			
			
			
			*/
			
			//APPROCCIO 3
			for(Adiacenza a : this.dao.getAdiacenza(idMap)) {
					Graphs.addEdge(this.grafo, a.getA1(), a.getA2(),
							a.getPeso());
					
			}

			System.out.println("GRAFO CREATO!");
			System.out.println("# VERTICI: " + grafo.vertexSet().size());
			System.out.println("# ARCHI: " + grafo.edgeSet().size());
			
	}
			public int nVertici() {
				return this.grafo.vertexSet().size();
			}
			
			public int nArchi() {
				return this.grafo.edgeSet().size();
			}


			public ArtObject getObject(int objectId) {
				// TODO Auto-generated method stub
				return idMap.get(objectId);
			}


			public int getComponenteConnessa(ArtObject vertice) {

				Set<ArtObject> visitati = new HashSet<>();
				DepthFirstIterator<ArtObject, DefaultWeightedEdge> it =
						new DepthFirstIterator<ArtObject, DefaultWeightedEdge>;
				
				while (it.hasNext()) {
					visitati.add(it.next());
				}
				
				return visitati.size();
			}
		
	}


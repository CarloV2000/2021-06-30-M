package it.polito.tdp.genes.model;

	public class Interaction{
		
		private String gene1 ;
		private String gene2 ;
		private String type ;
		private Double expressionCorr ;
		
		public Interaction(String gene1, String gene2, String type, Double expressionCorr) {
			super();
			this.gene1 = gene1;
			this.gene2 = gene2;
			this.type = type;
			this.expressionCorr = expressionCorr;
		}
		
		public String getGene1() {
			return gene1;
		}
		public void setGene1(String gene1) {
			this.gene1 = gene1;
		}
		public String getGene2() {
			return gene2;
		}
		public void setGene2(String gene2) {
			this.gene2 = gene2;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public Double getExpressionCorr() {
			return expressionCorr;
		}
		public void setExpressionCorr(Double expressionCorr) {
			this.expressionCorr = expressionCorr;
		}
}

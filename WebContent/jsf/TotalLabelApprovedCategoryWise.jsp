<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">
	<f:view>
	<h:form>
	
	<br/><br/>
	
	
	<hr style="border-top:7px #D0D3D4;border-top-style:dashed;"></hr>
	<div class="row">
	<div align="center">
	<h:outputText value=" Category Wise Total Label Approval Report"
		style="COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: 35px;font-family:Monotype Corsiva;">
													</h:outputText>
	</div>
	</div>
	<hr style="border-top:7px #D0D3D4;border-top-style:dashed;"></hr>
	<div class="form-group">
	
	<div class="row" align="center">
	
	<div class="col-md-2">
	</div>
	
	<div class="col-md-2">
	
	</div>
	
	<div class="col-md-2">
	
	</div>
	
	<div class="col-md-2" align="right">
	
	</div>
	
	
	<div class="col-md-2" align="left">
	
	</div>
<div class="col-md-2">
	
	</div>
	</div>
	</div>
	
	
	<div class="row" align="center">
	<rich:spacer height="25px"/>
	</div>
	
	
	<div class="row" align="center">
	
	<h:commandButton value="Print" styleClass="btn btn-primary" 
	action="#{totalLabelApprovedCategoryWiseAction.printReport}"></h:commandButton>
	<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/MIS/pdf/#{totalLabelApprovedCategoryWiseAction.pdfName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{totalLabelApprovedCategoryWiseAction.printFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>
					
					
					
					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/MIS/pdf/#{totalLabelApprovedCategoryWiseAction.excelName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text224"
							value="Download Excel"
							rendered="#{totalLabelApprovedCategoryWiseAction.printFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>
					
					
	<h:commandButton value="Reset" action="#{totalLabelApprovedCategoryWiseAction.reset}" styleClass="btn btn-default"></h:commandButton>
	</div>
	
	
	<div class="row" align="center">
	<rich:spacer height="25px"/>
	</div>
	
	
	
	</h:form>
	</f:view>
	
	
	
	
	
</ui:composition>
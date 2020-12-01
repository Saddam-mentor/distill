<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">


	<h:form>





		<f:view>
			<div class="form-group">
				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>
				<div>
					<a4j:outputPanel id="msg">
						<h:messages errorStyle="color:red"
							style="font-size: 14px;font-weight: bold"
							styleClass="generalExciseStyle" layout="table" id="messages"
							infoStyle="color:green">
						</h:messages>
					</a4j:outputPanel>
				</div>

				<div class="row " align="center">
					<div>
						<h3>
							
							<h:outputText value="SHEERA STATEMENT FORMAT FOR PORTAL "
								styleClass="generalHeaderOutputTable"
								style="font-weight: bold; font-size: 40px;text-decoration: underline;margin-top: 2px;font-family:Monotype Corsiva;font-size:30px;">
							</h:outputText>
						</h3>
					</div>
				</div>

				<div class="form-group newsdiv">
					<div class=" row col-md-12">
						<rich:spacer height="20px" />
					</div>

					
					

					<div class="row ">
						<rich:spacer height="30px" />
					</div>


					<div class="col-md-12" align="center">


						<h:commandButton action="#{sheera_StatementAction.print}"
							value="Print Report"
							style="background-color:#C5C5C5; font-size: large;" />

						<h:outputLink styleClass="outputLinkEx"
							value="/doc/ExciseUp/WholeSale/pdf//#{sheera_StatementAction.pdfname}"
							target="_blank">
							<h:outputText styleClass="outputText" id="text223"
								value="View Report"
								rendered="#{sheera_StatementAction.printFlag==true}"
								style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
						</h:outputLink>



					</div>
					<div class="row " align="center">
						<rich:spacer height="30px"></rich:spacer>
					</div>
					
					<div class="row " align="center">
						<rich:spacer height="30px"></rich:spacer>
					</div>
					
					<rich:spacer height="20px" />
				</div>
			</div>
		</f:view>
	</h:form>
</ui:composition>
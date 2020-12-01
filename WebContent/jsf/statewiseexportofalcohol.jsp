<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">
	<f:view>
		<h:form style="background-color:whitesmoke;">
<head>
		<style>
		.table{
		 background-color:#deeaee ;
		
		}
		.TableHead
		{
		 background-color:#92a8d1 ;
		}
		
.button {
  border: 1px solid black;
  background-color: white;
  color: black;
  padding: 5px 14px;
  font-size: 13px;
  cursor: pointer;
}
.success {
background-color: lightgreen;
  border-color: #4CAF50;
   border-radius: 3px;
}
.warning {
  border-color: #ffad33;
  background-color:lightyellow;
  border-radius: 3px;
}

hr.style-one {
border: 0;
height: 1px;
width :80% ;
background: #333;
background-image: -webkit-linear-gradient(left, #ccc, #333, #ccc);
background-image: -moz-linear-gradient(left, #ccc, #333, #ccc);
background-image: -ms-linear-gradient(left, #ccc, #333, #ccc);
background-image: -o-linear-gradient(left, #ccc, #333, #ccc);
}
.inputtext {
	border-radius: 6px;
	padding: 5px 5px;
	height: 30px;
	width: 250px;
	box-shadow: 1px 1px 15px lightsteelblue;
	border: 1px solid #669999;
}	
</style>
<link
    rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.0.0/animate.min.css" />		
			
		
		</head>
			<div class="form-group">
				<div class="row" align="center">
					<a4j:outputPanel id="msg">
						<div class="row col-md-12 wow shake" style="margin-top: 10px;">
							<h:messages errorStyle="color:red"
								style="font-size: 18px;font-weight: bold"
								styleClass="generalExciseStyle" layout="table"
								infoStyle="color:green" />
						</div>
					</a4j:outputPanel>
				</div>
				<div class="row " align="center">
					<rich:spacer height="10px"></rich:spacer>
				</div>
				<h:inputHidden value="#{statewiseexportofalcohol_action.hidden}"></h:inputHidden>
				<div class="animate__animated animate__bounce" align="center">
					<div style="padding: 10px 0px;  background-color:#92a8d1 ;">
						
						<h2>
							<h:outputText
								value="Statewise Export Of Alcohol"
								styleClass="generalHeaderOutputTable"
								style="FONT-FAMILY: 'Microsoft Sans Serif'; color:white ;font-weight: bold; font-size: 15px;margin-top: 2px;font-size:40px;letter-spacing:2px;">
							</h:outputText>
						</h2>
					</div>
				</div>
			   <hr class="style-one"/>
               	<div class="row">
					<div align="center">
						<h:outputLabel
							value="#{statewiseexportofalcohol_action.loginUserNm}"
							style="COLOR: #000000; FONT-SIZE: x-large;"></h:outputLabel>
					</div>
				</div>
				<div class="row " align="center">
					<rich:spacer height="10px"></rich:spacer>
				</div>
				<div class="row">
					<div align="center">
						<h:outputLabel
							value="#{statewiseexportofalcohol_action.loginUserAdrs}"
							style="COLOR: #000000; FONT-SIZE: medium;"></h:outputLabel>
					</div>
	</div>
	<hr class="style-one"/>
</div>

                    <div class="row" align="center">
                    <div class="col-md-2"></div>
					<div class="col-md-2" align="right">
						<h:outputText value="Select Year :"
							style="FONT-SIZE:small; FONT-WEIGHT: bold;"></h:outputText>
					</div>
					<div class="col-md-2" align="left">
						<h:selectOneMenu 
							value="#{statewiseexportofalcohol_action.yearr}"
							valueChangeListener="#{statewiseexportofalcohol_action.getdat}"
						    style="height: 25px; width : 150px;" onchange="this.form.submit();">
						    <f:selectItems
							value="#{statewiseexportofalcohol_action.year}"/>
						</h:selectOneMenu>
					</div>
					<div class="col-md-2" align="right">
						<h:outputText value="Select Month :"
							style="FONT-SIZE: small; FONT-WEIGHT: bold;"></h:outputText>
					</div>
					<div class="col-md-2" align="left">
						<h:selectOneMenu
							value="#{statewiseexportofalcohol_action.montth}"
							valueChangeListener="#{statewiseexportofalcohol_action.getdata}"
						    style="height: 25px; width : 150px;" onchange="this.form.submit();">
						   <f:selectItems
							value="#{statewiseexportofalcohol_action.month}" />
						</h:selectOneMenu>
					</div>
					<div class="col-md-2"></div>
				</div>
			
			<rich:spacer height='20px'></rich:spacer>
			<div align="center">
			<rich:dataTable columnClasses="columnClass1"
													headerClass="TableHead" footerClass="TableHead"
													rowClasses="TableRow1,TableRow2" styleClass="table table-hover"
													width="90%"
													value="#{statewiseexportofalcohol_action.displaylist}" var="list">
                                                    
                                                    <rich:column>
														<f:facet name="header">
															<h:outputLabel value="Sr.NO"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="generalExciseStyle"
															  value="#{list.sr_no}" />
														</center>
													</rich:column>
													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="State Name"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="generalExciseStyle"
															  value="#{list.state_name}" />
														</center>
													</rich:column>

													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="Allotment in Month BL"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:inputText styleClass="inputtext"
															value="#{list.allot_bl}" />
														</center>
													</rich:column>

													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="Allotment in Month AL"
															
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:inputText styleClass="inputtext"
															value="#{list.allot_al}" />
														</center>
													</rich:column>
												
													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="Actual Lifting in Month (BL)"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:inputText styleClass="inputtext"
															  value="#{list.actual_bl}" />
														</center>
													</rich:column>
													
													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="Actual Lifting in Month (AL)"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:inputText styleClass="inputtext"
															  value="#{list.actual_al}" />
														</center>
													</rich:column>
												  <f:facet name="footer">
													</f:facet>
												</rich:dataTable>
												</div>
									      	     
									<rich:spacer height="20px"></rich:spacer>
									    <div align="center">
										<h:commandButton
										styleClass="btn btn-success btn-sm" 
										action="#{statewiseexportofalcohol_action.save}"
										value="Save" style=" width : 68px;"></h:commandButton>
										<rich:spacer width='20px'></rich:spacer>
										<h:commandButton
										styleClass="btn btn-warning btn-sm"
										action="#{statewiseexportofalcohol_action.reset}"
										value="Reset" style=" width : 68px;"></h:commandButton>
										
										</div>
									<rich:spacer height="20px"></rich:spacer>	
				
				</h:form>
				</f:view>
</ui:composition>
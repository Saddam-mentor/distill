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
  border-color: #4CAF50;
  color: green;
  border-radius: 3px;
}
.success:hover {
  background-color: #4CAF50;
  color: white;
}		
.warning {
  border-color: #ffad33;
  color: #ffad33;
  border-radius: 3px;
}

.warning:hover {
  background-color: #ffad33;
  color: white;
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
	text-decoration: none;
}	
.inputtext1 {
	border-radius: 6px;
	padding: 5px 5px;
	height: 30px;
	width: 185px;
	box-shadow: 1px 1px 15px lightsteelblue;
	border: 1px solid #669999;
	text-decoration: none;
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
								style="font-size: 20px;font-weight: bold"
								styleClass="generalExciseStyle" layout="table"
								infoStyle="color:green" />
						</div>
					</a4j:outputPanel>
				</div>
				<rich:spacer height="10px"></rich:spacer>
				<div class="animate__animated animate__bounce" align="center">
					<div style="padding: 1px 0px;  background-color:#92a8d1 ;">
						
						<h2>
							<h:outputText
								value="HBR 19-20 List "
								styleClass="generalHeaderOutputTable"
								style="FONT-FAMILY: 'Microsoft Sans Serif'; color:white ;font-weight: bold; font-size: 40px;margin-top: 2px;font-size:40px;letter-spacing:2px;">
							</h:outputText>
						</h2>
					</div>
				</div>

			</div>
			<rich:spacer height="20px"></rich:spacer>
			
			
			            
			            <hr class="style-one"/>
			             <div>
			            <rich:spacer height="20px"></rich:spacer>
			            </div>
			            <div>
			            <div align ="center" class="animate__animated animate__fadeInUpBig">
			            
			          
			            
			            
			            
			            </div>
			            
			            
			            
			            </div>
			            <div> <rich:spacer height="30px"></rich:spacer>
			            
			            
			            
			            </div>
			            <div align="center">
			            <rich:dataTable columnClasses="columnClass1"
													headerClass="TableHead" footerClass="TableHead"
													rowClasses="TableRow1,TableRow2" styleClass="table table-hover"
													width="100%" rows="20"
													value="#{hbr_19_20_list_action.displaylist}" var="list">

													<rich:column filterBy="#{list.hbr_id}" filterEvent="onkeyup" sortBy="#{list.hbr_id}">
														<f:facet name="header" >
															<h:outputLabel value="HBR ID"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="generalExciseStyle"
															
																value="#{list.hbr_id}" />
														</center>
													</rich:column>

													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="HBR Name"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="generalExciseStyle"
															
																value="#{list.hbr_nm}" />
														</center>
													</rich:column>

													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="Mobile Number"
															
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="generalExciseStyle"
															
																value="#{list.mobile_num}" />
														</center>
													</rich:column>
												  <f:facet name="footer">
														<rich:datascroller  />
													</f:facet>
												</rich:dataTable>
			             </div>
			             
		</h:form>
	</f:view>
</ui:composition>

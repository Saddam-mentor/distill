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
				<h:inputHidden value="#{productionandconsumptionofalcohol_action.hidden}"></h:inputHidden>
				<div class="animate__animated animate__bounce" align="center">
					<div style="padding: 10px 0px;  background-color:#92a8d1 ;">
						
						<h2>
							<h:outputText
								value="Production And Consumption Of Alcohol"
								styleClass="generalHeaderOutputTable"
								style="FONT-FAMILY: 'Microsoft Sans Serif'; color:white ;font-weight: bold; font-size: 20px;margin-top: 2px;font-size:40px;letter-spacing:2px;">
							</h:outputText>
						</h2>
					</div>
				</div>
			   <hr class="style-one"/>
               	<div class="row">
					<div align="center">
						<h:outputLabel
							value="#{productionandconsumptionofalcohol_action.loginUserNm}"
							style="COLOR: #000000; FONT-SIZE: x-large;"></h:outputLabel>
					</div>
				</div>
				<div class="row " align="center">
					<rich:spacer height="10px"></rich:spacer>
				</div>
				<div class="row">
					<div align="center">
						<h:outputLabel
							value="#{productionandconsumptionofalcohol_action.loginUserAdrs}"
							style="COLOR: #000000; FONT-SIZE: medium;"></h:outputLabel>
					</div>
	</div>
	<hr class="style-one"/>
</div>

                    <div class="row" align="center">
					<div class="col-md-2" align="right">
						<h:outputText value="Select Year :"
							style="FONT-SIZE:small; FONT-WEIGHT: bold;"></h:outputText>
					</div>
					<div class="col-md-2" align="left">
						<h:selectOneMenu 
							value="#{productionandconsumptionofalcohol_action.yearr}"
						    style="height: 25px; width : 150px;" onchange="this.form.submit();">
						    <f:selectItems
							value="#{productionandconsumptionofalcohol_action.year}"/>
						</h:selectOneMenu>
					</div>
					<div class="col-md-2" align="right">
						<h:outputText value="Select Month :"
							style="FONT-SIZE: small; FONT-WEIGHT: bold;"></h:outputText>
					</div>
					<div class="col-md-2" align="left">
						<h:selectOneMenu
							value="#{productionandconsumptionofalcohol_action.montth}"
						    style="height: 25px; width : 150px;" onchange="this.form.submit();">
						   <f:selectItems
							value="#{productionandconsumptionofalcohol_action.month}" />
						</h:selectOneMenu>
					</div>
					<div class="col-md-2" align="right">
						<h:outputText value="Select Spirit Type :"
							style="FONT-SIZE:small; FONT-WEIGHT: bold;"></h:outputText>
					</div>
					<div class="col-md-2" align="left">
						<h:selectOneMenu
						    value="#{productionandconsumptionofalcohol_action.spriit_type}"
						    style="height: 25px; width : 150px;" onchange="this.form.submit();">
						     <f:selectItem itemLabel="--Select--" />
						    <f:selectItem itemValue="ENA" itemLabel="ENA" />
							<f:selectItem itemValue="RS" itemLabel="RS" />
							<f:selectItem itemValue="Absolute Alcohol" itemLabel="Absolute Alcohol" />
							<f:selectItem itemValue="DS And SDS" itemLabel="DS And SDS" />
							<f:selectItem itemValue="Power Alcohol" itemLabel="Power Alcohol" />
						</h:selectOneMenu>
					</div>
					<div class="col-md-1"></div>
				</div>
			
			<rich:spacer height='20px'></rich:spacer>
			
			<div align="center">
										   <table id="table" style="border-collapse: collapse;border: 1px solid black;width:80%" class="table table-hover">
										       <tr style="background-color:#92a8d1;">
										       <th style="margin:5px; text-align:center ; border: 1px solid #dddddd;padding:5px;width:300px;color:white;" >
										        <h:outputText value="Description"></h:outputText>
										       </th>
										       <th style="margin:5px; text-align:center ; border: 1px solid #dddddd;padding:5px;width:300px;color:white;" >
										        <h:outputText value="BL"></h:outputText>
										       </th>
										       <th style="margin:5px; text-align:center ; border: 1px solid #dddddd;padding:5px; width:200px;color:white;">
										        <h:outputText value="AL"/>
										       </th>
										       </tr>
										        <tr>
										       <th style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;">
										        <h:outputText value="Production"></h:outputText>
										       </th>
										       <td style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;">
										        <h:inputText value="#{productionandconsumptionofalcohol_action.production_bl}" 
										        styleClass="inputtext"></h:inputText>
										       </td>
										       <td style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;">
										        <h:inputText value="#{productionandconsumptionofalcohol_action.production_al}" 
										       styleClass="inputtext"></h:inputText>
										       </td>
										       </tr>
										        <tr >
										       <th style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;" >
										        <h:outputText value="Import Out Of State"></h:outputText>
										       </th>
										       <td style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;">
										        <h:inputText value="#{productionandconsumptionofalcohol_action.import_outofstate_bl}" 
										        styleClass="inputtext"></h:inputText>
										       </td>
										       <td style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;">
										        <h:inputText value="#{productionandconsumptionofalcohol_action.import_outofstate_al}" 
										        styleClass="inputtext"></h:inputText>
										       </td>
										       </tr>
										        <tr>
										       <th style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;">
										        <h:outputText value="Import Out Of India"></h:outputText>
										       </th>
										        <td style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;">
										        <h:inputText value="#{productionandconsumptionofalcohol_action.import_outofindia_bl}" 
										       styleClass="inputtext"></h:inputText>
										       </td>
										        <td style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;">
										        <h:inputText value="#{productionandconsumptionofalcohol_action.import_outofindia_al}" 
										        styleClass="inputtext"></h:inputText>
										       </td>
										       </tr>
										       
										       <tr>
										       <th style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;">
										        <h:outputText value="Consumption (For Production Of CL And FL in U.P Only)"></h:outputText>
										       </th>
										        <td style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;">
										        <h:inputText value="#{productionandconsumptionofalcohol_action.consumption_bl}" 
										        styleClass="inputtext"></h:inputText>
										       </td>
										        <td style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;">
										        <h:inputText value="#{productionandconsumptionofalcohol_action.consumption_al}" 
										        styleClass="inputtext"></h:inputText>
										       </td>
										       </tr>
										       <tr>
										       <th style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;">
										        <h:outputText value="Consumption(For Industrial Use in U.P Only)"></h:outputText>
										       </th>
										        <td style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;">
										        <h:inputText value="#{productionandconsumptionofalcohol_action.saleinup_drink_bl}" 
										        styleClass="inputtext"></h:inputText>
										       </td>
										        <td style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;">
										        <h:inputText value="#{productionandconsumptionofalcohol_action.saleinup_drink_al}" 
										        styleClass="inputtext"></h:inputText>
										       </td>
										       </tr>
										       <tr>
										       <th style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;">
										        <h:outputText value="Export Out Of U.P in India Only(For Portable Use)"></h:outputText>
										       </th>
										        <td style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;">
										        <h:inputText value="#{productionandconsumptionofalcohol_action.saleoutstate_drink_bl}" 
										        styleClass="inputtext"></h:inputText>
										       </td>
										        <td style="margin: 5px; text-align:center ;border: 1px solid #dddddd;padding:5px;">
										        <h:inputText value="#{productionandconsumptionofalcohol_action.saleoutstate_drink_al}" 
										        styleClass="inputtext"></h:inputText>
										       </td>
										       </tr>
										       <tr>
										       <th style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;">
										        <h:outputText value="Export Out Of U.P in India Only(For Industrial Use)"></h:outputText>
										       </th>
										        <td style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;">
										        <h:inputText value="#{productionandconsumptionofalcohol_action.saleoutstate_other_bl}" 
										        styleClass="inputtext"></h:inputText>
										       </td>
										        <td style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;">
										        <h:inputText value="#{productionandconsumptionofalcohol_action.saleoutstate_other_al}" 
										        styleClass="inputtext"></h:inputText>
										       </td>
										       </tr>
										       <tr>
										       <th style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;">
										        <h:outputText value="Export Out Of India"></h:outputText>
										       </th>
										        <td style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;">
										        <h:inputText value="#{productionandconsumptionofalcohol_action.saleoutcountry_drink_bl}" 
										        styleClass="inputtext"></h:inputText>
										       </td>
										        <td style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;">
										        <h:inputText value="#{productionandconsumptionofalcohol_action.saleoutcountry_drink_al}" 
										        styleClass="inputtext"></h:inputText>
										       </td>
										       </tr>
										        <tr>
										       <th style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;">
										        <h:outputText value="Wastage"></h:outputText>
										       </th>
										        <td style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;">
										        <h:inputText value="#{productionandconsumptionofalcohol_action.wastage_bl}" 
										        styleClass="inputtext"></h:inputText>
										       </td>
										        <td style="margin: 5px; text-align:center ; border: 1px solid #dddddd;padding:5px;">
										        <h:inputText value="#{productionandconsumptionofalcohol_action.wastage_al}" 
										        styleClass="inputtext"></h:inputText>
										       </td>
										       </tr>
										   </table>
										</div>	
									<rich:spacer height="20px"></rich:spacer>
									    <div align="center">
										<h:commandButton
										styleClass="btn btn-success btn-sm" 
										action="#{productionandconsumptionofalcohol_action.save}"
										value="Save" style=" width : 68px;"></h:commandButton>
										<rich:spacer width='20px'></rich:spacer>
										<h:commandButton
										styleClass="btn btn-warning btn-sm"
										action="#{productionandconsumptionofalcohol_action.reset}"
										value="Reset" style=" width : 68px;"></h:commandButton>
										
										</div>
									<rich:spacer height="20px"></rich:spacer>	
				
				</h:form>
				</f:view>
</ui:composition>
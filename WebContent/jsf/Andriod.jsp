
<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:rich="http://richfaces.org/rich"
	xmlns:a4j="http://richfaces.org/a4j">
 
 
 
 


	<f:view>

		<style>
.my {
	display: inline-block;
	overflow: hidden;
}

.my:hover {
	display: inline-block;
	overflow: hidden;
	transform: scale(1.5);
	-ms-transform: scale(1.5); /* IE 9 */
	-moz-transform: scale(1.5); /* Firefox */
	-webkit-transform: scale(1.5); /* Safari and Chrome */
	-o-transform: scale(1.5); /* Opera */
}


.rich-tab-header {
	 
	padding-top: 10px;
	padding-right: 10px;
	padding-bottom: 2px;
	padding-left: 10px;
	text-align: center;
	color:#131383;
font-weight: bold;
}
</style>
		<h:form style="FONT-SIZE: large;">
			<h:inputHidden value="#{homePageAction.loginCheck}" />
			<h:panelGroup
				rendered="#{!homePageAction.loginFlg and  !homePageAction.loginDashboardFlg}">

				 






				<section>
					<div class="row " align="right"
						style="position: absolute; width: 100%; z-index: 1; margin-top: -4px;">













					</div>



				</section>
				
				<section>
					<div class="row"
						style="position: fixed;; z-index: 1; left: 0; width: 16%; padding-top: 2.5em;">


						<div class="slideouticons horizontal">

							<input type="checkbox" id="togglebox2" /> <label
								for="togglebox2" class="mainlabel" title="Policies"><h:graphicImage
									alt="Policies" value="/images/policy.png"></h:graphicImage></label>

							<div class="iconswrapper">
								<ul>
									<li><a target="_blank"
										href="/doc/ExciseUp/PdfHome//Molasses.pdf"
										title="Molasses Policy"><h:graphicImage
												value="/images/p1.png"></h:graphicImage></a></li>
									<li><a target="_blank"
										href="/doc//ExciseUp//PdfHome//Distillery.pdf"
										title="Excise Policy"><h:graphicImage
												value="/images/p2.png"></h:graphicImage></a></li>

								</ul>
							</div>

						</div>

						<div align="left" style="padding-top: 2.5em;">
							<a target="_self"
								href="https://play.google.com/store/apps/details?id=com.mentorinfotech.excise"
								title="Barcode Scanner App"> <h:graphicImage
									value="/images/android.png"></h:graphicImage></a>

						</div>

					</div>

				</section>
				<div class="row" align="center">

					<div class="col-md-12 col-sm-12  col-xs-12 "
						style="background-color: #c9e9fe; font: 20px;">
						<marquee
							onmouseover="this.setAttribute('scrollamount',0,0);this.stop();"
							onmouseout="this.setAttribute('scrollamount', 2, 0);this.start();">

							<H5>
								<h:outputText value="#{homePageAction.newsText }"
									style="COLOR: #000000;  "></h:outputText>
							</H5>


						</marquee>
					</div>
				</div>
				<section>


					<div class="row">
						<div class="banner  img-responsive">
							<h:graphicImage value="/images/ww.JPG" style="width:100%;"></h:graphicImage>

						</div>
					</div>
				</section>

				<div class="row " align="center"
					style="position: absolute; width: 100%; z-index: 1; margin-top: -128px; align: center">
					<div class=" col-sm-1 col-md-1 col-lg-1  hidden-xs" align="center"></div>
					<div class=" col-sm-10 col-md-10 col-lg-10 col-xs-12 "
						align="center">
						<div class=" col-sm-2 col-md-2  " align="center">
							<a href="/portal/Home/Home/License/Services"><h:graphicImage
									class="my" value="/images/h6.png" style="width:100%;"></h:graphicImage></a>
						</div>
						<div class=" col-sm-2 col-md-2  " align="center">
							<a href="/portal/Home/Home/License//Units"><h:graphicImage
									class="my" value="/images/h2.png" style="width:108%;"></h:graphicImage></a>
						</div>
						<div class=" col-sm-2 col-md-2  " align="center">
							<a href="/portal/Home/Home/License//UnitsRegistered"> <h:graphicImage
									class="my" value="/images/h3.png" style="width:100%;"></h:graphicImage></a>
						</div>
						<div class=" col-sm-2 col-md-2  " align="center">
							<a href="/portal/Home/Home/Challan"><h:graphicImage
									value="/images/h4.png" class="my" style="width:100%;"></h:graphicImage></a>
						</div>
						<div class=" col-sm-2 col-md-2  " align="center">
							<a href="/portal/Home/Home/License/Permits"> <h:graphicImage
									class="my" value="/images/h5.png" style="width:100%;"></h:graphicImage></a>
						</div>
						<div class=" col-sm-2 col-md-2  " align="center">
							<a href="/portal/Home/Home/License/Permit"><h:graphicImage class="my" value="/images/h1.png" style="width:100%;"></h:graphicImage></a>
						</div>

					</div>
					<div class=" col-sm-1 col-md-1 col-lg-1  hidden-xs" align="center"></div>


				</div>
				<div class="row">
					<rich:spacer height="30px"></rich:spacer>
				</div>


				<div class="row">
					

					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
						<div class="row ">
							<rich:spacer height="15px"></rich:spacer>
						</div>
						<div class="row " align="center" style="color: #000000;">
							Your Problems, Our Solutions... Lets enhance your Portal
							Experience Together.</div>
						<div class="row ">
							<rich:spacer height="10px"></rich:spacer>
						</div>

						<div class="row ">
						
						<div class="col-md-2 col-sm-12 col-xs-12 col-lg-2 "
								align="center" style="padding: 0px;">
								<div class="col-md-12 col-sm-12 col-xs-12" align="center"
									style="padding: 2px;">
									<a target="_blank" href="https://www.upexciseonline.in/doc/ExciseUp/JanitNew.pdf"><h:graphicImage
											value="/img/psdds.png" style="width:100%;"></h:graphicImage></a>
								</div>
							</div>
							
							<div class="col-md-2 col-sm-12 col-xs-12 col-lg-2 "
								align="center" style="padding: 0px;">
								<div class="col-md-12 col-sm-12 col-xs-12" align="center"
									style="padding: 2px;">
										<a target="_blank" href="/portal/Home/Home/License/Circulars/"  > <h:graphicImage
											value="/img/vpc.png" style="width:100%;"></h:graphicImage></a>
								</div>
							</div>
							<div class="col-md-2 col-sm-12 col-xs-12 col-lg-2 "
								align="center" style="padding: 0px;">
								<div class="col-md-12 col-sm-12 col-xs-12" align="center"
									style="padding: 2px;">
									<a target="_self" href="/portal/Home/Home/Help+Desk"><h:graphicImage
											value="/img/manuals.png" style="width:100%;"></h:graphicImage></a>
								</div>
							</div>

							<div class="col-md-2 col-sm-12 col-xs-12 col-lg-2 "
								align="center" style="padding: 0px;">
								<div class="col-md-12 col-sm-12 col-xs-12" align="center"
									style="padding: 2px;">
									<h:graphicImage value="/img/hdn.png" style="width:100%;"></h:graphicImage>
								</div>
							</div>
							<div class="col-md-3 col-sm-12 col-xs-12 col-lg-2" align="center"
								style="padding: 0px;">
								<div class="col-md-12 col-sm-12 col-xs-12" align="center"
									style="padding: 2px;">
									<a target="_self" href="/portal/Home/Home/Help+Desk/CD"><h:graphicImage
											value="/img/tt.png" style="width:100%;"></h:graphicImage></a>
								</div>
							</div>




							<div class="col-md-2 col-sm-12 col-xs-12 col-lg-2 "
								align="center" style="padding: 0px;">
								<div class="col-md-12 col-sm-12 col-xs-12" align="center"
									style="padding: 2px;">
									<a target="_self" href="/portal/Home/Home/Help+Desk/FP"><h:graphicImage
											value="/img/FP.png" style="width:100%;"></h:graphicImage></a>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="row " align="center"
					style="background-color: rgba(73, 204, 210, 0.24);">
					<div class="col-md-1 hidden-sm hidden-xs col-lg-1  " align="center">
					</div>
					<div class="col-md-10 col-sm-12 col-xs-12 col-lg-10  newsdiv"
						align="center">
						<div class="row " style="color: #000;">
							<u>IMPORTANT GUIDLINES FOR PORTAL USE</u>
						</div>
						<div class="row " align="left">
							<h5 style="color: #000;">
								1.<span><h:outputText
										value=" Please Do not share user Login Credentials"></h:outputText></span>
							</h5>
						</div>
						<div class="row " align="left">
							<h5 style="color: #000;">
								2. <span> <h:outputText
										value=" Make Sure that you keep changing your Password ( At Least in Every 60 Days )"></h:outputText></span>
							</h5>
						</div>
						<div class="row " align="left">
							<h5 style="color: #000;">
								3. <span><h:outputText
										value="  Please Do not Submit any Challan in Manual Mode at Treasury. Always use the Online facility provided on this portal to Deposit Challans"></h:outputText></span>
							</h5>
						</div>
						<div class="row " align="left">
							<h5 style="color: #000;">
								4. <span> <h:outputText
										value=" While Submitting the Online Applications, Use the Links provided  on Application Page to Submit the Required Fee. Do Not Use Online Challan Link of Home Page"></h:outputText></span>
							</h5>
						</div>
						<div class="row " align="left">
							<h5 style="color: #000;">
								5. <span> <h:outputText
										value="  Follow the Instructions Depicted in Help Documents. Make Use of Trouble Ticket in Defining Your Problem and You May Contact on Help Desk Numbers"></h:outputText></span>
							</h5>
						</div>
					</div>
					<div class="col-md-1 hidden-sm hidden-xs col-lg-1  " align="center">
					</div>
				</div>
				<div class="row ">
					<rich:spacer height="3px"></rich:spacer>
				</div>
				<div class="row " align="center">

					<div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 "
						align="center">

						<div class="col-md-2 col-sm-2 col-xs-2 col-lg-2 " align="center">
							<a target="_self" href="http://up.gov.in/"> <h:graphicImage
									value="/img/tngovin.png" style="width:100%;"></h:graphicImage></a>
						</div>
						<div class="col-md-2 col-sm-2 col-xs-2 col-lg-2 " align="center">
							<a target="_self" href="https://dmdashboard.nic.in/"> <h:graphicImage
									value="/img/dashboard.png" style="width:100%;"></h:graphicImage></a>
						</div>

						<div class="col-md-2 col-sm-2 col-xs-2 col-lg-2 " align="center">
							<a target="_self" href="http://niveshmitra.up.nic.in/"> <h:graphicImage
									value="/img/nivesh.png" style="width:100%;"></h:graphicImage></a>
						</div>
						<div class="col-md-2 col-sm-2 col-xs-2 col-lg-2 " align="center">
							<a target="_self" href="https://www.india.gov.in/"> <h:graphicImage
									value="/img/indiagovin.png" style="width:100%;"></h:graphicImage></a>
						</div>
						<div class="col-md-2 col-sm-2 col-xs-2 col-lg-2 " align="center">
							<a target="_self" href="http://www.makeinindia.com/home"> <h:graphicImage
									value="/img/makeinindia.png" style="width:100%;"></h:graphicImage></a>
						</div>
						<div class="col-md-2 col-sm-2 col-xs-2 col-lg-2 " align="center">
							<a target="_self" href="https://www.digitalindia.gov.in/"><h:graphicImage
									value="/img/digital-india.png" style="width:100%;"></h:graphicImage></a>
						</div>

					</div>
				</div>


			</h:panelGroup>





			<h:panelGroup
				rendered="#{homePageAction.loginFlg  and !homePageAction.dashcomm and !homePageAction.psec}">
				<table width="100%" valign="top" align="center">

					<tr>
						<td width="100%" align="center" colspan="2">

							<div align="center" class="container">

								<div class="form-group">
									<div class="table-responsive">
										<div class="row">
											<rich:spacer height="10px"></rich:spacer>
										</div>





										<div class="row col-md-12">

											<div class="col-md-12">
												<h:dataTable rows="50"
													value="#{homePageAction.menuLinkList}" var="list">
													<h:column rendered="#{homePageAction.columnRender1}">
														<h:panelGroup rendered="#{list.cellRender1}">
															<div align="center">
																<h:outputLink value="/#{list.linkURL1}">
																	<h:graphicImage class="iconImageEffect"
																		value="#{list.linkIcon1}" />
																	<br />
																</h:outputLink>
															</div>
														</h:panelGroup>
													</h:column>

													<h:column rendered="#{homePageAction.columnRender2}">
														<h:panelGroup rendered="#{list.cellRender2}">
															<div align="center">
																<h:outputLink value="/#{list.linkURL2}">
																	<h:graphicImage class="iconImageEffect"
																		value="#{list.linkIcon2}" />
																	<br />
																</h:outputLink>
															</div>
														</h:panelGroup>
													</h:column>

													<h:column rendered="#{homePageAction.columnRender3}">
														<h:panelGroup rendered="#{list.cellRender3}">
															<div align="center">
																<h:outputLink value="/#{list.linkURL3}">
																	<h:graphicImage class="iconImageEffect"
																		value="#{list.linkIcon3}" />
																	<br />
																</h:outputLink>
															</div>
														</h:panelGroup>
													</h:column>
													<h:column rendered="#{homePageAction.columnRender4}">
														<h:panelGroup rendered="#{list.cellRender4}">
															<div align="center">
																<h:outputLink value="/#{list.linkURL4}">
																	<h:graphicImage class="iconImageEffect"
																		value="#{list.linkIcon4}" />
																	<br />
																</h:outputLink>
															</div>
														</h:panelGroup>
													</h:column>
													<h:column rendered="#{homePageAction.columnRender5}">
														<h:panelGroup rendered="#{list.cellRender5}">
															<div align="center">
																<h:outputLink value="/#{list.linkURL5}">
																	<h:graphicImage class="iconImageEffect"
																		value="#{list.linkIcon5}" />
																	<br />
																</h:outputLink>
															</div>
														</h:panelGroup>
													</h:column>
													<h:column rendered="#{homePageAction.columnRender6}">
														<h:panelGroup rendered="#{list.cellRender6 }">
															<div align="center">
																<h:outputLink value="/#{list.linkURL6}">
																	<h:graphicImage class="iconImageEffect"
																		value="#{list.linkIcon6}" />
																	<br />
																</h:outputLink>
															</div>
														</h:panelGroup>
													</h:column>



												</h:dataTable>

											</div>


										</div>
									</div>
								</div>



							</div>


						</td>
					</tr>

				</table>
			</h:panelGroup>

		</h:form>

			<h:form>
				<h:panelGroup rendered="#{homePageAction.dashcomm}">
					<h:inputHidden value="#{homePageAction.loginCheck}" />
					<h:inputHidden>#{homePageAction.dashhidden}</h:inputHidden>
					 <div class="row col-md-12" style="background-color:#fff; font-size: 2em;color: black; " align="left"> Welcome EXCISE-COMMISSIONER.</div>
					 <div class="row col-md-12" style="height:2px;background-color:#000;" >	</div>
					<div align="center" class="row col-md-12"
						style="background-color: #2d2e2e; padding-left: 0px; padding-right: 0px;">
						<div class="row col-md-2">
<div align="center" class="row col-md-12">
								<rich:spacer height="40px"></rich:spacer>

							</div>

							<div align="center" class="row col-md-12"
								style="FONT-SIZE: large;">

								<h:commandButton value="Approvals "  
									action="#{homePageAction.appv}" style=" width : 100%;height: 40px;"></h:commandButton>
							</div>
							<div align="center" class="row col-md-12">
								<rich:spacer height="10px"></rich:spacer>

							</div>
							<div align="center" class="row col-md-12"
								style="FONT-SIZE: large;">

								<h:commandButton value="Trackings "  
									action="#{homePageAction.trck}"  style=" width : 100%;height: 40px;"></h:commandButton>
							</div>

							<div align="center" class="row col-md-12">
								<rich:spacer height="10px"></rich:spacer>

							</div>
							<div align="center" class="row col-md-12"
								style="FONT-SIZE: large;">

								<h:commandButton value="Reports"  
									action="#{homePageAction.reprt}"  style=" width : 100%;height: 40px;"></h:commandButton>
							</div>
<div align="center" class="row col-md-12">
								<rich:spacer height="40px"></rich:spacer>

							</div>


<div align="center" class="row col-md-12"
							style="FONT-SIZE: large; background-color: #fff">
							Prime Reports</div>

<div align="left" class="row col-md-12" style="padding: 0px;">
							<a href="/portal/MIS/Track+N+Trace/BrandWise+Sale+Summary"
								style="color: #fff">BrandWise Sale / Duty </a>
						</div>
												
						<div align="left" class="row col-md-12" style="padding: 0px;">
							<a
								href="/portal/MIS/Track+N+Trace/Dispatches+of+Distillery-Brewery"
								style="color: #fff">Monthly Sale / Duty</a>
						</div>
						<div align="left" class="row col-md-12" style="padding: 0px;">
							<a href="/portal/MIS/Wholesale/Dispatches+from+Wholesale"
								style="color: #fff">Dispatches From Wholesale</a>
						</div>
						
					<div align="left" class="row col-md-12" style="padding: 0px;">

							<a href="/portal/MIS/Retail/MGQ-Achievement" style="color: #fff">CL
								MGQ Achievement</a>
						</div>
						
						<div align="left" class="row col-md-12" style="padding: 0px;">

							<a href="/portal/MIS/Retail/FL+Lifting+Comparison" style="color: #fff">FL
								Lifting Comparison</a>
						</div>
						
						<div align="left" class="row col-md-12" style="padding: 0px;">

							<a href="/portal/MIS/Retail/Beer+Lifting+Comparison" style="color: #fff">Beer
								Lifting Comparison</a>
						</div>

						</div>
						<div class="row col-md-10" style="background-color: #FFFFFF;">


							<div class="container">

								<h:panelGroup rendered="#{homePageAction.trackflg}">

									<div class="col-md-12 col-sm-12  col-xs-12  ">

										<div class="col-md-1 hidden-sm  hidden-xs "></div>
										<div class="col-md-10 col-sm-12  col-xs-12 ">



											<h2 style="COLOR: #400040; text-align: center;">Portal
												Tracking</h2>



										</div>
										<div class="col-md-1 col-sm-12  col-xs-12  " align="center">
											 
										</div>
									</div>

<div class="col-md-12 row  " align="center">
											<h:commandButton value="Back"
												style="display:block; width: 30%;"
												action="#{homePageAction.bck}"></h:commandButton>
										</div>


									<div class="row ">


										<div class="col-md-2 col-sm-2  col-xs-2  " align="right"
											style="padding: 5px;">
											<h:graphicImage value="/images/track.png" class="my"
												style="display:block;width: 40%;" id="img13"
												data-image-alignment="center"></h:graphicImage>
										</div>
										<a target="_self" href="/portal/MIS/Registrations/BWFL+2A-2B-2C-2D"><div
												class="col-md-9 col-sm-9  col-xs-9  newsdiv"
												style="background-color: white; opacity: 0.8;"
												align="center">
												<div class="row " style="color: #000;">Applications
													For BWFL2A / BWFL2B / BWFL2C / BWFL2D</div>



											</div></a>
									</div>

									<div class="row ">


										<div class="col-md-2 col-sm-2  col-xs-2  " align="right"
											style="padding: 5px;">
											<h:graphicImage value="/images/track.png" class="my"
												style="display:block;width: 40%;" id="img121"
												data-image-alignment="center"></h:graphicImage>
										</div>
										<a target="_self" href="/portal/MIS/Registrations/CL2-FL2-FL2B-FL2D"><div
												class="col-md-9 col-sm-9  col-xs-9  newsdiv"
												style="background-color: white; opacity: 0.8;"
												align="center">
												<div class="row " style="color: #000;">Applications
													For CL2 / FL2 / FL2B / FL2D</div>



											</div></a>
									</div>

									<div class="row ">


										<div class="col-md-2 col-sm-2  col-xs-2  " align="right"
											style="padding: 5px;">
											<h:graphicImage value="/images/track.png" class="my"
												style="display:block;width: 40%;" id="img113"
												data-image-alignment="center"></h:graphicImage>
										</div>
										<a target="_self" href="/portal/MIS/Registrations/B"><div
												class="col-md-9 col-sm-9  col-xs-9  newsdiv"
												style="background-color: white; opacity: 0.8;"
												align="center">
												<div class="row " style="color: #000;">Applications
													For Brands / Labels Registration</div>



											</div></a>
									</div>
									
									
									<div class="row ">


										<div class="col-md-2 col-sm-2  col-xs-2  " align="right"
											style="padding: 5px;">
											<h:graphicImage value="/images/track.png" class="my"
												style="display:block;width: 40%;" id="bfs"
												data-image-alignment="center"></h:graphicImage>
										</div>
										<a target="_self" href="/portal/Licensing/Janhit/Tracking"><div
												class="col-md-9 col-sm-9  col-xs-9  newsdiv"
												style="background-color: white; opacity: 0.8;"
												align="center">
												<div class="row " style="color: #000;">Useful Public Services</div>



											</div></a>
									</div>
									<div class="row ">


										<div class="col-md-2 col-sm-2  col-xs-2  " align="right"
											style="padding: 5px;">
											<h:graphicImage value="/images/track.png" class="my"
												style="display:block;width: 40%;" id="as"
												data-image-alignment="center"></h:graphicImage>
										</div>
										<a target="_self" href="/portal/Licensing/Nivesh+Mitra/Tracking"><div
												class="col-md-9 col-sm-9  col-xs-9  newsdiv"
												style="background-color: white; opacity: 0.8;"
												align="center">
												<div class="row " style="color: #000;">Nivesh Mitra</div>



											</div></a>
									</div>
									
								</h:panelGroup>
								<h:panelGroup rendered="#{homePageAction.approvalflg}">

									<div class="col-md-12 col-sm-12  col-xs-12  ">

										<div class="col-md-1 hidden-sm  hidden-xs "></div>
										<div class="col-md-10 col-sm-12  col-xs-12 ">



											<h2 style="COLOR: #400040; text-align: center;">Portal
												Approvals</h2>



										</div>
										<div class="col-md-1 col-sm-12  col-xs-12  " align="center">
											 
										</div>
										
									</div>
<div class="col-md-12 row  " align="center">
											<h:commandButton value="Back"
												style="display:block; width: 30%;"
												action="#{homePageAction.bck}"></h:commandButton>
										</div>

<div class="row ">


										<div class="col-md-2 col-sm-2  col-xs-2  " align="right"
											style="padding: 5px;">
											<h:graphicImage value="/images/permit.png" 
												style="display:block;width: 40%;" id="img1zf3"
												data-image-alignment="center"></h:graphicImage>
										</div>
										<a target="_self" href="/portal/Registration+Approval/ImportingUnit"><div
												class="col-md-9 col-sm-9  col-xs-9  newsdiv"
												style="background-color: white; opacity: 0.8;"
												align="left">
												<div class="row " style="color: #000;">New
													Registration Approval For Importing Units</div>


												<div class="row ">अन्य देशो से आयातित मदिरा के
													इकाई  (Importing Unit) के रजिस्ट्रेशन  हेतु
													अप्रूवल</div>
											</div></a>
									</div>

									<div class="row ">


										<div class="col-md-2 col-sm-2  col-xs-2  " align="right"
											style="padding: 5px;">
											<h:graphicImage value="/images/permit.png" 
												style="display:block;width: 40%;" id="img1zz3"
												data-image-alignment="center"></h:graphicImage>
										</div>
										<a target="_self" href="/portal/Registration+Approval/Bond+New+App"><div
												class="col-md-9 col-sm-9  col-xs-9  newsdiv"
												style="background-color: white; opacity: 0.8;"
												align="left">
												<div class="row " style="color: #000;">New
													Applications For BWFL2A / BWFL2B / BWFL2C / BWFL2D</div>


												<div class="row ">अन्य राज्यों से आयातित मदिरा के
													गोदामों (BWFL2A/BWFL2B/BWFL2C/BWFL2D ) के नए गोदामों हेतु
													अप्रूवल</div>
											</div></a>
									</div>

									<div class="row ">


										<div class="col-md-2 col-sm-2  col-xs-2  " align="right"
											style="padding: 5px;">
											<h:graphicImage value="/images/permit.png" 
												style="display:block;width: 40%;" id="aca"
												data-image-alignment="center"></h:graphicImage>
										</div>
										<a target="_self" href="/portal/Registration+Approval/Bond+Renewel+App"><div
												class="col-md-9 col-sm-9  col-xs-9  newsdiv"
												style="background-color: white; opacity: 0.8;"
												align="left">
												<div class="row " style="color: #000;">Renewal
													Applications For BWFL2A / BWFL2B / BWFL2C / BWFL2D</div>


												<div class="row ">अन्य राज्यों से आयातित मदिरा के
													गोदामों (BWFL2A/BWFL2B/BWFL2C/BWFL2D ) के नवीनीकरण हेतु
													अप्रूवल</div>
											</div></a>
									</div>
<div class="row ">


										<div class="col-md-2 col-sm-2  col-xs-2  " align="right"
											style="padding: 5px;">
											<h:graphicImage value="/images/permit.png" 
												style="display:block;width: 40%;" id="aa"
												data-image-alignment="center"></h:graphicImage>
										</div>
										<a target="_self" href="/portal/Registration+Approval/Wholesale+Renewel+App"><div
												class="col-md-9 col-sm-9  col-xs-9  newsdiv"
												style="background-color: white; opacity: 0.8;"
												align="left">
												<div class="row " style="color: #000;">Renewal
													Applications For FL2D</div>


												<div class="row ">अन्य राज्यों से आयातित मदिरा के
													गोदामों ( FL2D ) के नवीनीकरण हेतु
													अप्रूवल</div>
											</div></a>
									</div>
									<div class="row ">


										<div class="col-md-2 col-sm-2  col-xs-2  " align="right"
											style="padding: 5px;">
											<h:graphicImage value="/images/permit.png" 
												style="display:block;width: 40%;" id="img123"
												data-image-alignment="center"></h:graphicImage>
										</div>
										<a target="_self" href="/portal/Registration+Approval/Brand-Label+Approval"><div
												class="col-md-9 col-sm-9  col-xs-9  newsdiv"
												style="background-color: white; opacity: 0.8;"
												align="left">
												<div class="row " style="color: #000;">Applications
													For Brands / Labels Registration</div>


												<div class="row ">ब्रांड अवं लेबल हेतु अप्रूवल</div>
											</div></a>
									</div>
									
									
									
									<div class="row ">


										<div class="col-md-2 col-sm-2  col-xs-2  " align="right"
											style="padding: 5px;">
											<h:graphicImage value="/images/permit.png" 
												style="display:block;width: 40%;" id="bb"
												data-image-alignment="center"></h:graphicImage>
										</div>
										<a target="_self" href="/portal/Licensing/Nivesh+Mitra/Approval"><div
												class="col-md-9 col-sm-9  col-xs-9  newsdiv"
												style="background-color: white; opacity: 0.8;"
												align="left">
												<div class="row " style="color: #000;">Nivesh Mitra Approval</div>


												<div class="row "> 
													निवेश मित्र अप्रूवल</div>
											</div></a>
									</div>
									
									<div class="row ">


										<div class="col-md-2 col-sm-2  col-xs-2  " align="right"
											style="padding: 5px;">
											<h:graphicImage value="/images/permit.png" 
												style="display:block;width: 40%;" id="bffb"
												data-image-alignment="center"></h:graphicImage>
										</div>
										<a target="_self" href="/portal/Licensing/Janhit/Approval"><div
												class="col-md-9 col-sm-9  col-xs-9  newsdiv"
												style="background-color: white; opacity: 0.8;"
												align="left">
												<div class="row " style="color: #000;">Useful Public Services Approval</div>


												<div class="row "> 
													जनहित  अप्रूवल</div>
											</div></a>
									</div>
									<div class="row ">


										<div class="col-md-2 col-sm-2  col-xs-2  " align="right"
											style="padding: 5px;">
											<h:graphicImage value="/images/permit.png" 
												style="display:block;width: 40%;" id="cc"
												data-image-alignment="center"></h:graphicImage>
										</div>
										<a target="_self" href="/portal/Registration+Approval//ENA+Approval"><div
												class="col-md-9 col-sm-9  col-xs-9  newsdiv"
												style="background-color: white; opacity: 0.8;"
												align="left">
												<div class="row " style="color: #000;">ENA Approval</div>


												<div class="row "> 
													ENA अप्रूवल</div>
											</div></a>
									</div>
									
									
									<div class="row ">


										<div class="col-md-2 col-sm-2  col-xs-2  " align="right"
											style="padding: 5px;">
											<h:graphicImage value="/images/permit.png" 
												style="display:block;width: 40%;" id="cjjc"
												data-image-alignment="center"></h:graphicImage>
										</div>
										<a target="_self" href="/portal/Distilleries/Export+Order+Approval"><div
												class="col-md-9 col-sm-9  col-xs-9  newsdiv"
												style="background-color: white; opacity: 0.8;"
												align="left">
												<div class="row " style="color: #000;">Export Orders Approval</div>


												 
											</div></a>
									</div>
								</h:panelGroup>
								<!-- report pane -->
								<h:panelGroup rendered="#{homePageAction.reportflg}">

									<div class="col-md-12 col-sm-12  col-xs-12  ">

										<div class="col-md-2 hidden-sm  hidden-xs "></div>
										<div class="col-md-8 col-sm-12  col-xs-12 ">



											<h2 style="COLOR: #400040; text-align: center;">Portal
												Reports</h2>



										</div>
										<div class="col-md-2 col-sm-12  col-xs-12  " align="center">
											 
										</div>
									</div>

<div class="col-md-12 row  " align="center">
											<h:commandButton value="Back"
												style="display:block; width: 30%;"
												action="#{homePageAction.bck}"></h:commandButton>
										</div>






									<div class="row ">

<div class="col-md-4 col-sm-4  col-xs-4 newsdiv"
											style="height: 250px">
											 
											
											<div align="center" class="row col-md-12"
												style="FONT-SIZE: large; background-color: #fff">Financials</div>
												
												
												
												<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a
													href="/portal/MIS/Financial/Challan+Deposit+Report">Challan Deposit </a>

											</div>
											<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a
													href="/portal/MIS/Financial/G6Report">Challan by G6 Detail</a>

											</div> 
											<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a
													href="/portal/MIS/Financial/HeadWise+Challan+Distillery-Brewery+Report">Headwise Challan from Distillery/Brewery</a>

											</div> 
											<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a
													href="/portal/MIS/Financial/Treasury+Rajkosh+Scroll+Report">Month Wise Treasury/Rajkosk Scroll Update</a>

											</div> 
											<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a
													href="/portal/MIS/Financial/Challan+Success+Report">Challan Success Details</a>

											</div> 
											<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a
													href="/portal/MIS/Financial/ScanningFee">Scanning Fee</a>

											</div> 
											 
</div>
										<div class="col-md-3 col-sm-3  col-xs-3 newsdiv"
											style="height: 270px">









											<div align="center" class="row col-md-12"
												style="FONT-SIZE: large; background-color: #fff">
												Liquor Production Units</div>

<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a href="/portal/MIS/Track+N+Trace/Distillery-Brewery+Production">BrandWise
													Distillery-Brewery Wise Production </a>
											</div>
											<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a href="/portal/MIS/Track+N+Trace/BrandWise+Sale+Summary">BrandWise
													Sale Summary </a>
											</div>
											<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a href="/portal/MIS/Track+N+Trace/Sale+In+Cases">Sale
													In Cases </a>
											</div>

											<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a
													href="/portal/MIS/Track+N+Trace/DistrictWise+WholeSale+Receipt">District
													Wise Sale</a>
											</div>
											<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a
													href="/portal/MIS/Track+N+Trace/Dispatches+of+Distillery-Brewery">Sale
													in BL and Duty</a>
											</div>
											<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a href="/portal/MIS/Track+N+Trace/Cumulative+Sale+And+Duty">Duty
													From Distillery/Brewery</a>
											</div>
											<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a href="/portal/MIS/Track+N+Trace/BWFL_FL2D_sale_duty">Duty
													From BWFL/FL2D</a>
											</div>
											<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a href="/portal/MIS/Track+N+Trace/Duty+Report">GatepassWise
													Duty </a>
											</div>

											<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a
													href="/portal/MIS/Track+N+Trace/GatepasswiseDispatchCLFLBEER">Dispatches
													Within UP </a>
											</div>


										</div>

										<div class="col-md-4 col-sm-4  col-xs-4 newsdiv"
											style="height: 250px">
											<div align="center" class="row col-md-12"
												style="FONT-SIZE: large; background-color: #fff">RETAIL</div>


											<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a href="/portal/MIS/Retail/Shop+Report">Shop Details</a>

											</div>
											<div align="center" class="row col-md-12"
												style="padding: 0px;">

												<a href="/portal/MIS/Retail/Retailwise+Dispatch">Retailwise
													Dispatches</a>

											</div>

											<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a href="/portal/MIS/Retail/Shopwise-Brandwise+Dispatch">RetailWise-BrandWise
													Dispatches</a>

											</div>
											<div align="center" class="row col-md-12"
												style="padding: 0px;">

												<a href="/portal/MIS/Retail/MGQ-Achievement">CL MGQ
													Achievement</a>
											</div>
											<div align="center" class="row col-md-12"
												style="padding: 0px;">

												<a href="/portal/MIS/Retail/Cumulative+MGQ+Achievement">Cumulative
													MGQ Achievement</a>
											</div>
											<div align="center" class="row col-md-12"
												style="padding: 0px;">

												<a href="/portal/MIS/Retail/Highest-Lowest+Lifting">Highest
													Lowest Lifting</a>
											</div>



										</div>

									</div>

									<div class="row ">


										

										<div class="col-md-4 col-sm-4  col-xs-4 newsdiv"
											style="height: 120px">
											
											<div align="center" class="row col-md-12"
												style="FONT-SIZE: large; background-color: #fff">Spirit</div>
												
												
												<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a
													href="/portal/MIS/Spirit/Production">Spirit Production</a>
											</div>
												
												<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a
													href="/portal/MIS/Spirit/Sale+-++Consumption">Spirit Consumption</a>
											</div>
												
												
												<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a
													href="/portal/MIS/Spirit/Tank+Coversion+Report">Spirit Vat Conversion</a>
											</div>
											</div>
											
											<div class="col-md-3 col-sm-3  col-xs-3 newsdiv"
											style="height: 120px">
											<div align="center" class="row col-md-12"
												style="FONT-SIZE: large; background-color: #fff">Power Alchohol</div>
												
												
												
												<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a
													href="/portal/MIS/Spirit/Tank+Coversion+Report">Indent Wise Balance Lifting</a>
											</div>
												
												<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a
													href="/portal/MIS/FL41/Power+Alcohol+Indent+Tracking">District Wise Tracking</a>
											</div>
											
											<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a
													href="/portal/MIS/FL41/StateWise+Tracking">State Wise Tracking</a>
											</div>
											</div>
											
											
<div class="col-md-4 col-sm-4  col-xs-4 newsdiv"
											style="height: 120px">

											<div align="center" class="row col-md-12"
												style="FONT-SIZE: large; background-color: #fff">ENA</div>


											<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a
													href="/portal/MIS/Spirit/ENA+Approvals">ENA Approvals</a>

											</div>
											<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a
													href="/portal/MIS/Spirit/Request+of+CL-ENA+by+Seller">ENA Purchase</a>

											</div>

											 

										</div>
										
											
											 

									</div>
									<div class="row ">

<div class="col-md-4 col-sm-4  col-xs-4 newsdiv"
											style="height: 120px">

											<div align="center" class="row col-md-12"
												style="FONT-SIZE: large; background-color: #fff">Liqour
												Indents</div>


											<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a
													href="/portal/Licensing/Liquor+Indents/Indent+Status+Details">Indent
													Status</a>

											</div>
											<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a
													href="/portal/Licensing/Liquor+Indents/District+Wise+Pending">District
													Wise Pending</a>

											</div>

											<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a
													href="/portal/Licensing/Liquor+Indents/Distillery_Brewery+Wise+Pending">Distillery/Brewery
													Wise Pending</a>
											</div>

										</div>
										

										<div class="col-md-3 col-sm-3  col-xs-3 newsdiv"
											style="height: 120px">
											
											<div align="center" class="row col-md-12"
												style="FONT-SIZE: large; background-color: #fff">Public Services</div>
												
												
												
												<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a
													href="/portal/MIS/License/Occasional+Bar+Report">FL11 Details</a>

											</div>
											<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a
													href="/portal/MIS/License/Status+Occasional+Bar+Applications">FL11 Status</a>

											</div>
											 
												
											</div>

										<div class="col-md-4 col-sm-4  col-xs-4 newsdiv"
											style="height: 120px">
											<div align="center" class="row col-md-12"
												style="FONT-SIZE: large; background-color: #fff">Wholesale</div>


											<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a href="/portal/MIS/Wholesale/Dispatches+from+Wholesale">Dispatches
													From Wholesale</a>
											</div>
											<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a href="/portal/MIS/Wholesale/Recieving+at+Wholesale">Recieving
													At WholeSale </a>
											</div>

											<div align="center" class="row col-md-12"
												style="padding: 0px;">
												<a href="/portal/MIS/Wholesale/Wholesale+Stock">WholeSale
													Register</a>
											</div>




										</div>

										
										
										
										
										
										 
									</div>
								</h:panelGroup>
								

								
<h:panelGroup rendered="#{homePageAction.main_flg eq 'T' }">
								<h:panelGroup
									rendered="#{homePageAction.flag_detail eq false and homePageAction.flag_btn eq true and homePageAction.approvalflg eq false and homePageAction.reportflg eq false and homePageAction.trackflg eq false }">
<div class="row"><rich:spacer height="30px"></rich:spacer></div>
	<div class="row"> <div class="col-md-10">
	<div class="row col-md-12">
									 	<div class="col-md-1" style="font-size: 1.5em;border-style: solid;font-weight: bolder;color: #a10b19;">
									#{homePageAction.renewbondcount}
									</div>	
										<div class="col-md-2" align="left">
										Pending for BWFL Licence Renewal
									</div>
									<div class="col-md-1" style="font-size: 1.5em;border-style: solid;font-weight: bolder;color: #a10b19;">
									#{homePageAction.newbondcount}
									</div>	
										<div class="col-md-2" align="left">
										Pending for BWFL New Licence
									</div>
									<div class="col-md-1" style="font-size: 1.5em;border-style: solid;font-weight: bolder;color: #a10b19;">
									#{homePageAction.brandcount}
									</div>	
										<div class="col-md-2" align="left">
										Pending for Brand / Label 
									</div>
									 <div class="col-md-1" style="font-size: 1.5em;border-style: solid;font-weight: bolder;color: #a10b19;">
									#{homePageAction.enacount}
									</div>	
										<div class="col-md-2" align="left">
										Pending for ENA 
									</div>
									 	</div>
										</div>
										
										<div class="col-md-2" style="color: blue;">
										<h:commandButton action="GO" value="View ChargeWise Pending Wholesale Applications" style="background: transparent;border: none;white-space: normal"></h:commandButton>
										</div>
										
										</div> 
										<div class="row"><rich:spacer height="20px"></rich:spacer></div>
										<div class="row"> <div class="col-md-10">
	<div class="row col-md-12">
									 	<div class="col-md-1" style="font-size: 1.5em;border-style: solid;font-weight: bolder;color: #a10b19;">
									#{homePageAction.newimpotunitcount}
									</div>	
										<div class="col-md-2" align="left">
										Pending for New Importing Units
									</div>
									<div class="col-md-1" style="font-size: 1.5em;border-style: solid;font-weight: bolder;color: #a10b19;">
									#{homePageAction.fl2drenewcount}
									</div>	
										<div class="col-md-2" align="left">
										Pending for FL2D Renewal
									</div>
									<div class="col-md-1" style="font-size: 1.5em;border-style: solid;font-weight: bolder;color: #a10b19;">
									#{homePageAction.niveshmitracount}
									</div>	
										<div class="col-md-2" align="left">
										Pending for Nivesh Mitra 
									</div>
									 <div class="col-md-1" style="font-size: 1.5em;border-style: solid;font-weight: bolder;color: #a10b19;">
									#{homePageAction.usefulpubliccount}
									</div>	
										<div class="col-md-2" align="left">
										Pending for Useful Public Services 
									</div>
									 	</div>
										</div>
										
										<div class="col-md-2" style="color: blue;">
										 
										</div>
										
										</div> 
										<div class="row"><rich:spacer height="20px"></rich:spacer></div>
										<div class="row" style="height:2px;background-color:#000;" >
										</div>
										 
										<div class="row"><rich:spacer height="30px"></rich:spacer></div>
										
										 
										<div class="row">
										
										<div class="  col-md-3 ">
 
															<a target="_self" href="/portal/Graphical View"> <h:graphicImage
																		value="/images/GV.png"
																		style="display:block; width: 100%;"  
																		data-image-alignment="center"></h:graphicImage></a> 
																 

															 
															
															 

														</div>
										<div class="  col-md-3 ">
 
															 
																<h:commandButton style="display:block; width: 100%;"
																	action="#{homePageAction.gojanhit}"
																	image="/images/JSV.png"  />

															 
															
															 

														</div>
														<div class="  col-md-3 ">
 
															 
																<h:commandButton style="display:block; width: 100%;"
																	action="#{homePageAction.gonivesh}"
																	image="/images/NMSV.png"  />

															 
															
															 

														</div>
														<div class="  col-md-3 ">
 
															 
																<h:commandButton style="display:block; width: 100%;"
																	action="#{homePageAction.goliqrindnt}"
																	image="/images/LISV.png"  />

															 
															
															 

														</div>
														</div>
										
										 
											<div class="row"><rich:spacer height="15px"></rich:spacer></div>
								 
 
<div class="row"><rich:spacer height="20px"></rich:spacer></div>
 
								<h:panelGroup  >
									<div class="panel-body" style="height: 300px;">
									
									<div class="panel-heading" style="background-color: #b7dee8;"> 
										Stock Boxes At Unit</div>
										 
										 <div class="row"><rich:spacer height="35px"></rich:spacer></div>
										<div class="row col-md-12" >
											 

											<div class="col-md-4" align="left" style="FONT-SIZE: medium;">
												<b>Manufacturing Unit - </b>    
											</div>



											<div class="col-md-2" align="left" style="FONT-SIZE: small;">
												 FL :
												 <h:commandButton value="#{homePageAction.mufl}" action="#{homePageAction.dist_mufl}"
												 style=" background-color: Transparent;background-repeat:no-repeat;border: none;cursor:pointer;overflow: hidden; color:blue">
												 </h:commandButton>
												 
											</div>
											<div class="col-md-2" align="left" style="FONT-SIZE: small;">
												BEER :   <h:commandButton value="#{homePageAction.mubeer}" action="#{homePageAction.dist_mubeer}"
												 style=" background-color: Transparent;background-repeat:no-repeat;border: none;cursor:pointer;overflow: hidden; color:blue">
												 </h:commandButton>
												
											
											</div>

											<div class="col-md-2" align="left" style="FONT-SIZE: small;">
												CL : <h:commandButton value="#{homePageAction.mucl}" action="#{homePageAction.dist_mucl}"
												 style=" background-color: Transparent;background-repeat:no-repeat;border: none;cursor:pointer;overflow: hidden; color:blue">
												 </h:commandButton>  
											
											</div>
											 


										</div>
<div class="row"><rich:spacer height="20px"></rich:spacer></div>
										<div class="row col-md-12">





											<div class="col-md-4" align="left" style="FONT-SIZE: medium;">
												<b>Bonds -  </b>    
											</div>

											<div class="col-md-2" align="left" style="FONT-SIZE: small;">
												LAB/BEER : 
												<h:commandButton value="#{homePageAction.bond2}" action="#{homePageAction.bond_bond2}"
												 style=" background-color: Transparent;background-repeat:no-repeat;border: none;cursor:pointer;overflow: hidden; color:blue">
												 </h:commandButton>
												
												
												 
											</div>
											<div class="col-md-2" align="left" style="FONT-SIZE: small;">
												WINE/FL : 
												<h:commandButton value="#{homePageAction.bond1}" action="#{homePageAction.bond_bond1}"
												 style=" background-color: Transparent;background-repeat:no-repeat;border: none;cursor:pointer;overflow: hidden; color:blue">
												 </h:commandButton>
												
											</div>
											 
										 
											 

										</div>
										<div class="row"><rich:spacer height="20px"></rich:spacer></div>
										<div class="row col-md-12" >
											 

											<div class="col-md-4" align="left" style="FONT-SIZE: medium;">
												<b>Wholesale - </b>    
											</div>



											<div class="col-md-2" align="left" style="FONT-SIZE: small;">
												 FL : <h:commandButton value="#{homePageAction.wfl}" action="#{homePageAction.wholesale_wfl}"
												 style=" background-color: Transparent;background-repeat:no-repeat;border: none;cursor:pointer;overflow: hidden; color:blue">
												 </h:commandButton> 
												 
											</div>
											<div class="col-md-2" align="left" style="FONT-SIZE: small;">
												BEER : <h:commandButton value="#{homePageAction.wbeer}" action="#{homePageAction.wholesale_wbeer}"
												 style=" background-color: Transparent;background-repeat:no-repeat;border: none;cursor:pointer;overflow: hidden; color:blue">
												 </h:commandButton>   
												  
											
											</div>

											<div class="col-md-2" align="left" style="FONT-SIZE: small;">
												CL :  <h:commandButton value="#{homePageAction.wcl}" action="#{homePageAction.wholesale_wcl}"
												 style=" background-color: Transparent;background-repeat:no-repeat;border: none;cursor:pointer;overflow: hidden; color:blue">
												 </h:commandButton>    
												
												 
											</div>
											 


										</div>
									</div>
								
									
									</h:panelGroup>
									
									
								<div class="row"><rich:spacer height="2px"></rich:spacer></div>
								<div class="  col-md-12">
								<div class="panel panel-default">
									<div class="panel-heading" style="background-color: #b7dee8;"> 
										
										<h:commandButton style="background: transparent;border: none;white-space: normal;width: 100%;"
																	action="#{homePageAction.gounits}"
																	value="Units Registered"  />
										</div>
										
								</div>

							</div>
							
							
							 
							
							
</h:panelGroup>
</h:panelGroup>
<h:panelGroup rendered="#{homePageAction.dist_mufl_flg eq 'T' }">
									<div class="row" align="center">

							<rich:spacer height="20px" />
						</div>
									<div class="row"  style="FONT-SIZE: medium;">
												<h:outputText value="Stock Boxes At Unit-#{homePageAction.type_ }" style="FONT-SIZE: medium;"></h:outputText>
											</div>
											<div class="row" align="center">

							<rich:spacer height="20px" />
						</div>
							<div style="overflow-y:scroll;  height: 300px; align:center;"> 		
									<div class="row" align="center">
						
						<div class="col-md-12">
						
							<rich:dataTable id="table716"   width="100%"
								value="#{homePageAction.dist_mufllist}" var="list"
								headerClass="TableHead" footerClass="TableHead"
								styleClass="DataTable" rowClasses="TableRow1,TableRow2"  >


								<rich:column>
									<f:facet name="header">
										<h:outputText value="Sr.No">
										</h:outputText>
									</f:facet>
									<h:outputText style="margin-left: 20px;" value="#{list.sno_mufl}"></h:outputText>
								</rich:column>

								

								

								<rich:column>
									<f:facet name="header">
										<h:outputText value="Distillery Name" />
									</f:facet>
									<center>
										<h:outputText styleClass="generalExciseStyle"
											value="#{list.distillery_name_mufl}" />
									</center>
								</rich:column>

								
								<rich:column  >
									<f:facet name="header">
										<h:outputText value="Cases">
										</h:outputText>
									</f:facet>

									<h:outputText value="#{list.mu_fl}"></h:outputText>
								</rich:column>

								

								

								

								
							</rich:dataTable>

								
						</div>
					</div>
					</div>
					<div class="row" align="center" style="margin-top: 15px;">
						<h:commandButton value="Close" class="btn btn-info"
							action="#{homePageAction.close_dist_mufl}"></h:commandButton>
					</div>
									</h:panelGroup>
									<h:panelGroup rendered="#{homePageAction.w_mufl_flg eq 'T' }">
									<div class="row" align="center">

							<rich:spacer height="20px" />
						</div>
									<div class="row"  style="FONT-SIZE: medium;">
												<h:outputText value="Stock Boxes At Unit-#{homePageAction.type_ }" style="FONT-SIZE: medium;"></h:outputText>
											</div>
											<div class="row" align="center">

							<rich:spacer height="20px" />
						</div>
									<div style="overflow-y:scroll;  height: 300px; align:center;"> 
									<div class="row" align="center">
						<div class="col-md-12">
							<rich:dataTable id="table76"  width="100%"
								value="#{homePageAction.bond_wholesale_mufllist}" var="list"
								headerClass="TableHead" footerClass="TableHead"
								styleClass="DataTable" rowClasses="TableRow1,TableRow2"  >


								<rich:column>
									<f:facet name="header">
										<h:outputText value="Sr.No">
										</h:outputText>
									</f:facet>
									<h:outputText style="margin-left: 20px;" value="#{list.srno}"></h:outputText>
								</rich:column>

								

								

								<rich:column>
									<f:facet name="header">
										<h:outputText value="District Name" />
									</f:facet>
									<center>
										<h:outputText styleClass="generalExciseStyle"
											value="#{list.district_name}" />
									</center>
								</rich:column>

								
<rich:column  >
									<f:facet name="header">
										<h:outputText value="Cases">
										</h:outputText>
									</f:facet>

									<h:outputText value="#{list.avalbottles}"></h:outputText>
								</rich:column>
								

								

								

								
							
							</rich:dataTable>

						</div>
					</div>
					</div>
					<div class="row" align="center" style="margin-top: 15px;">
						<h:commandButton value="Close" class="btn btn-info"
							action="#{homePageAction.close_dist_mufl}"></h:commandButton>
					</div>
									</h:panelGroup>

<h:panelGroup rendered="#{homePageAction.unitflg}">
									<div class="panel-body" style="height: 450px;">
									
									<div class="panel-heading" style="background-color: #b7dee8;"> 
										 Units Registered </div>
										 
										 <div class="row"><rich:spacer height="35px"></rich:spacer></div>
										<div class="row col-md-12" >
											<div class="col-md-2" align="left" style="FONT-SIZE: small;">
												<a target="_self" href="/portal/Home/Home/Sugar+Mills/List"><h:graphicImage
														value="/img/click.png"></h:graphicImage>Sugarmill-#{homePageAction.sgrmillcount}
												</a>
											</div>

											<div class="col-md-2" align="left" style="FONT-SIZE: small;">
												<a target="_self" href="/portal/Home/Home/Distilleries/List"><h:graphicImage
														value="/img/click.png"></h:graphicImage>Distillery:
													#{homePageAction.distcount} </a>
											</div>



											<div class="col-md-2" align="left" style="FONT-SIZE: small;">
												<a target="_self" href="/portal/Home/Home/Distilleries/List"><h:graphicImage
														value="/img/click.png"></h:graphicImage>Distillery
													Exp:#{homePageAction.expcount} </a>
											</div>
											<div class="col-md-2" align="left" style="FONT-SIZE: small;">
												<a target="_self" href="/portal/Home/Home/Brewery/List"><h:graphicImage
														value="/img/click.png"></h:graphicImage>Brewery-#{homePageAction.brewery}
												</a>
											</div>

											<div class="col-md-2" align="left" style="FONT-SIZE: small;">
												<a target="_self" href="/portal/Home/Home/Industries/List"><h:graphicImage
														value="/img/click.png"></h:graphicImage>Industries:#{homePageAction.indcount}
												</a>
											</div>
											<div class="col-md-2" align="left" style="FONT-SIZE: small;">
												<a target="_self" href="/portal/Home/Home/Industries/List"><h:graphicImage
														value="/img/click.png"></h:graphicImage>Industries
													Exp:#{homePageAction.indcountoup} </a>
											</div>


										</div>
<div class="row"><rich:spacer height="20px"></rich:spacer></div>
										<div class="row col-md-12">





											<div class="col-md-2" align="left" style="FONT-SIZE: small;">
												<a target="_self"
													href="/portal/Home/Home/License/BNWFL2/List"><h:graphicImage
														value="/img/click.png"></h:graphicImage>BWFL2A:#{homePageAction.bwfl2a}
												</a>
											</div>

											<div class="col-md-2" align="left" style="FONT-SIZE: small;">
												<a target="_self"
													href="/portal/Home/Home/License/BNWFL2/List"><h:graphicImage
														value="/img/click.png"></h:graphicImage>BWFL2B:#{homePageAction.bwfl2b}
												</a>
											</div>
											<div class="col-md-2" align="left" style="FONT-SIZE: small;">
												<a target="_self"
													href="/portal/Home/Home/License/BNWFL2/List"><h:graphicImage
														value="/img/click.png"></h:graphicImage>BWFL2C:#{homePageAction.bwfl2c}
												</a>
											</div>
											<div class="col-md-2" align="left" style="FONT-SIZE: small;">
												<a target="_self"
													href="/portal/Home/Home/License/BNWFL2/List"><h:graphicImage
														value="/img/click.png"></h:graphicImage>BWFL2D:#{homePageAction.bwfl2d}
												</a>
											</div>
											<div class="col-md-2" align="left" style="FONT-SIZE: small;">
												<a target="_self" href="/portal/Home/Home/License/FL2/List"><h:graphicImage
														value="/img/click.png"></h:graphicImage>FL2D:
													#{homePageAction.fl2d} </a>
											</div>
											<div class="col-md-2" align="left" style="FONT-SIZE: small;">
												<a target="_self" href="/portal/Home/Home/Shops/List/"><h:graphicImage
														value="/img/click.png"></h:graphicImage>Shops:#{homePageAction.shopcount}
												</a>
											</div>

										</div>
									</div>
									<div class="row"><rich:spacer height="15px"></rich:spacer></div>
									
									<div class="col-md-12 row  " align="center">
											<h:commandButton value="Back"
												style="display:block; width: 30%;"
												action="#{homePageAction.bck}"></h:commandButton>
										</div>
									
									</h:panelGroup>
<!-- nivesh mitra pane -->
<h:panelGroup rendered="#{homePageAction.janhitflg}">

									 
<div class="row"><rich:spacer height="15px"></rich:spacer></div>
								 
<div class="col-md-12 row  " align="center">
											<h:commandButton value="Back"
												style="display:block; width: 30%;"
												action="#{homePageAction.bck}"></h:commandButton>
										</div>
<div class="row"><rich:spacer height="15px"></rich:spacer></div>
								 

<div class="newsdiv  row" style="height: 600px;">


<div class="panel-heading" style="background-color: #b7dee8;">
												<a target="_self" href="/portal/Licensing/Janhit/Approval">JANHIT STATISTICAL FOR 2020-21
													 </a>
											</div>
												 
<div class="panel-body">
												<div class="row col-md-12" align="left">

													<h:graphicImage value="/img/arrow.jpg"></h:graphicImage>
													<i> <h:outputText value="For Approvals: "
															style="TEXT-DECORATION: underline; COLOR: #000040;" />
													</i>
												</div>
												<div class="row col-md-12">

													<div class="col-md-4" align="left">
														 
													</div>
													<div class="col-md-4" align="left">
														 
													</div>
													<div class="col-md-4" align="left"
														style="FONT-SIZE: small;">
														 
													</div>
												</div>

												<div class="row col-md-12"
													style="FONT-SIZE: large; background-color: #91fdd5;">
													<rich:spacer height="1px"></rich:spacer>
												</div>
												<div class="row col-md-12">

													<div class="col-md-2" align="left">
														<h:graphicImage value="/img/arrow.jpg"></h:graphicImage>
														<i> <h:outputText value="Status : "
																style="COLOR: #000040;" />
														</i>
													</div>

													<div class="col-md-2" align="right">
														<h:outputText value="Pending" style="COLOR: #006200;" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="Approved" style="COLOR: #006200;" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="Approved within 7 days" style="COLOR: #006200;" /> 
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="Rejected" style="COLOR: #006200;" />
													</div>
													<div class="col-md-2" align="right">

														<h:outputText value="Total" style="COLOR: #006200;" />
													</div>

												</div>
												<div class="row col-md-12"
													style="FONT-SIZE: large; background-color: #91fdd5;">
													<rich:spacer height="1px"></rich:spacer>
												</div>
												
												<div class="row col-md-12">

													<div class="col-md-2" align="left">
														<h:outputText value="Sacramental Wine "
															style="FONT-SIZE: small; COLOR: #000040;" />
													</div>

													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.janhitwinepen}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.janhitwineaprvd}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.janhitwineaprvdwthin7day}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.janhitwinerej}" />
													</div>
													<div class="col-md-2" align="right">

														<h:outputText
															value="#{homePageAction.janhitwinerej+homePageAction.janhitwineaprvd+homePageAction.janhitwinepen}" />
													</div>

												</div>
												<div class="row col-md-12">

													<div class="col-md-2" align="left">
														<h:outputText value=" Narcotic Drug Import"
															style="FONT-SIZE: small; COLOR: #000040;" />
													</div>

													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.janhitdugpen}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.janhitdugaprvd}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.janhitdugaprvdwithin7day}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.janhitdugrej}" />
													</div>
													<div class="col-md-2" align="right">

														<h:outputText
															value="#{homePageAction.janhitdugpen+homePageAction.janhitdugrej+homePageAction.janhitdugaprvd}" />
													</div>

												</div>
												
												<div class="row col-md-12">

													<div class="col-md-2" align="left">
														<h:outputText value=" Narcotic Drug Export"
															style="FONT-SIZE: small; COLOR: #000040;" />
													</div>

													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.janhitdugpenexp}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.janhitdugaprvdexp}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.janhitdugaprvdexpwithin7day}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.janhitdugrejexp}" />
													</div>
													<div class="col-md-2" align="right">

														<h:outputText
															value="#{homePageAction.janhitdugpenexp+homePageAction.janhitdugrejexp+homePageAction.janhitdugaprvdexp}" />
													</div>

												</div>

												<div class="row col-md-12">

													<div class="col-md-2" align="left">
														<h:outputText value=" Occassional Bar"
															style="FONT-SIZE: small; COLOR: #000040;" />
													</div>

													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.occpen}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.occaprv}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.occaprvwithin7day}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.occrej}" />
													</div>
													<div class="col-md-2" align="right">

														<h:outputText
															value="#{homePageAction.occaprv+homePageAction.occrej+homePageAction.occpen}" />
													</div>

												</div>
												<div class="row col-md-12">

													<div class="col-md-2" align="left">
														<h:outputText value="Spirits for Hospital,etc. "
															style="FONT-SIZE: small; COLOR: #000040;" />
													</div>

													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.janhitspiritpen}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.janhitspiritaprvd}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.janhitspiritaprvdwithin7day}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.janhitspiritrej}" />
													</div>
													<div class="col-md-2" align="right">

														<h:outputText
															value="#{homePageAction.janhitspiritrej+homePageAction.janhitspiritaprvd+homePageAction.janhitspiritpen}" />
													</div>

												</div>
												<div class="row col-md-12"
													style="FONT-SIZE: large; background-color: #91fdd5;">
													<rich:spacer height="1px"></rich:spacer>
												</div>

												<div class="  col-md-12" align="left"
													style="FONT-SIZE: small;">



													<h:graphicImage value="/img/arrow.jpg"></h:graphicImage>
													<i> <h:outputText value="Track Indents : "
															style="TEXT-DECORATION: underline; COLOR: #000040;" />
													</i>
													<rich:spacer width="12px"></rich:spacer>

													<a target="_self" href="/portal/Licensing/Janhit/Tracking"><h:outputText
															value="Janhit"
															style="FONT-SIZE: small; FONT-STYLE: italic; color: #1520FE;" />
													</a>
													<rich:spacer width="5px"></rich:spacer>
													<a target="_self"
														href="/portal/MIS/License/Occasional+Bar+Report"> <h:outputText
															value="OccasionalBar"
															style="FONT-SIZE: small; FONT-STYLE: italic; color: #1520FE;" /></a>
													<rich:spacer width="5px"></rich:spacer>
													<a target="_self"
														href="/portal/MIS/License/Search+Occasional+Bar+Applications"><h:outputText
															value="OccassionalBar Search"
															style="FONT-SIZE: small; FONT-STYLE: italic; color: #1520FE;" />
													</a>
													<rich:spacer width="5px"></rich:spacer>
													<a target="_self"
														href="/portal/MIS/License/Status+Occasional+Bar+Applications"><h:outputText
															value="OccassionalBar Status"
															style="FONT-SIZE: small; FONT-STYLE: italic; color: #1520FE;" />
													</a>
												</div>



											</div>
									<div class="row"><rich:spacer height="15px"></rich:spacer></div>		
											
					<div class="panel-heading" style="background-color: #b7dee8;">
												<a >Other Services
													 </a>
											</div>	
											<div class="row col-md-12" align="left">

													<h:graphicImage value="/img/arrow.jpg"></h:graphicImage>
													<i> <h:outputText value="For Approvals: "
															style="TEXT-DECORATION: underline; COLOR: #000040;" />
													</i>
												</div>
												<div class="row col-md-12">

													<div class="col-md-4" align="left">
														 
													</div>
													<div class="col-md-4" align="left">
														 
													</div>
													<div class="col-md-4" align="left"
														style="FONT-SIZE: small;">
														 
													</div>
												</div>

												<div class="row col-md-12"
													style="FONT-SIZE: large; background-color: #91fdd5;">
													<rich:spacer height="1px"></rich:spacer>
												</div>
												<div class="row col-md-12">

													<div class="col-md-2" align="left">
														<h:graphicImage value="/img/arrow.jpg"></h:graphicImage>
														<i> <h:outputText value="Status : "
																style="COLOR: #000040;" />
														</i>
													</div>

													<div class="col-md-2" align="right">
														<h:outputText value="Pending" style="COLOR: #006200;" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="Approved" style="COLOR: #006200;" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="Approved within 7 days" style="COLOR: #006200;" /> 
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="Rejected" style="COLOR: #006200;" />
													</div>
													<div class="col-md-2" align="right">

														<h:outputText value="Total" style="COLOR: #006200;" />
													</div>

												</div>
												<div class="row col-md-12"
													style="FONT-SIZE: large; background-color: #91fdd5;">
													<rich:spacer height="1px"></rich:spacer>
												</div>
												
												<div class="row col-md-12">

													<div class="col-md-2" align="left">
														<h:outputText value="BWFL Permits"
															style="FONT-SIZE: small; COLOR: #000040;" />
													</div>

													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.otherbwflpen}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.otherbwflaprvd}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.otherbwflaprvdwthin7day}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.otherbwflrej}" />
													</div>
													<div class="col-md-2" align="right">

														<h:outputText
															value="#{homePageAction.otherbwflrej+homePageAction.otherbwflaprvd+homePageAction.otherbwflpen}" />
													</div>

												</div>
												<div class="row col-md-12">

													<div class="col-md-2" align="left">
														<h:outputText value="Fl2D Permits"
															style="FONT-SIZE: small; COLOR: #000040;" />
													</div>

													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.otherfl2dpen}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.otherfl2daprvd}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.otherfl2daprvdwithin7day}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.otherfl2drej}" />
													</div>
													<div class="col-md-2" align="right">

														<h:outputText
															value="#{homePageAction.otherfl2dpen+homePageAction.otherfl2drej+homePageAction.otherfl2daprvd}" />
													</div>

												</div>
												
												<div class="row col-md-12">

													<div class="col-md-2" align="left">
														<h:outputText value="ENA Import"
															style="FONT-SIZE: small; COLOR: #000040;" />
													</div>

													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.otherenaimppen}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.otherenaimpaprvd}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.otherenaimpaprvdwithin7day}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.otherenaimprej}" />
													</div>
													<div class="col-md-2" align="right">

														<h:outputText
															value="#{homePageAction.otherenaimppen+homePageAction.otherenaimprej+homePageAction.otherenaimpaprvd}" />
													</div>

												</div>

												<div class="row col-md-12">

													<div class="col-md-2" align="left">
														<h:outputText value="ENA Export"
															style="FONT-SIZE: small; COLOR: #000040;" />
													</div>

													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.otherenaexppen}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.otherenaexpaprv}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.otherenaexpaprvwithin7day}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.otherenaexprej}" />
													</div>
													<div class="col-md-2" align="right">

														<h:outputText
															value="#{homePageAction.otherenaexpaprv+homePageAction.otherenaexprej+homePageAction.otherenaexppen}" />
													</div>

												</div>
												<div class="row col-md-12">

													<div class="col-md-2" align="left">
														<h:outputText value="IMFL Export"
															style="FONT-SIZE: small; COLOR: #000040;" />
													</div>

													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.otherimflexppen}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.otherimflexpaprvd}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.otherimflexpaprvdwithin7day}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.otherimflexprej}" />
													</div>
													<div class="col-md-2" align="right">

														<h:outputText
															value="#{homePageAction.otherimflexprej+homePageAction.otherimflexpaprvd+homePageAction.otherimflexppen}" />
													</div>

												</div>					
											
											<div class="row col-md-12"
													style="FONT-SIZE: large; background-color: #91fdd5;">
													<rich:spacer height="1px"></rich:spacer>
												</div>
												<div  >
													<rich:spacer height="20px"></rich:spacer>
												</div>
											
												</div>

											 



									 

									 
									 
								</h:panelGroup>
								<h:panelGroup rendered="#{homePageAction.niveshflg}">

									 
<div class="row"><rich:spacer height="15px"></rich:spacer></div>
								 
<div class="col-md-12 row  " align="center">
											<h:commandButton value="Back"
												style="display:block; width: 30%;"
												action="#{homePageAction.bck}"></h:commandButton>
										</div>
<div class="row"><rich:spacer height="15px"></rich:spacer></div>
								 

<div class="newsdiv  row" style="height: 400px;">


<div class="panel-heading" style="background-color: #b7dee8;">
												<a target="_self" href="/portal/Licensing/Janhit/Approval">NIVESH MITRA 
													 </a>
											</div>
												 
<div class="panel-body">
												<div class="row col-md-12" align="left">

													<h:graphicImage value="/img/arrow.jpg"></h:graphicImage>
													<i> <h:outputText value="For Approvals: "
															style="TEXT-DECORATION: underline; COLOR: #000040;" />
													</i>
												</div>
												<div class="row col-md-12" align="left"
													 >


													<a target="_self" 
														href="/portal/Licensing/Nivesh+Mitra/Approval"><h:outputText
															value="Industrial Alchohol : #{homePageAction.pencomm01}" /></a>
													<rich:spacer width="10px"></rich:spacer>
													<a target="_self" 
														href="/portal/Licensing/Nivesh+Mitra/Approval"> <h:outputText
															value="Distillery Estb.: #{homePageAction.pencomm05}" /></a>
													<rich:spacer width="10px"></rich:spacer>
													<a target="_self" 
														href="/portal/Licensing/Nivesh+Mitra/Approval"> <h:outputText
															value="Distillery Operation : #{homePageAction.pencomm07}" /></a>
												 



													<a target="_self" 
														href="/portal/Licensing/Nivesh+Mitra/Approval"> <h:outputText
															value="Brewery Estb.: #{homePageAction.pencomm08}" /></a>
													<rich:spacer width="22px"></rich:spacer>
													<a target="_self" 
														href="/portal/Licensing/Nivesh+Mitra/Approval"> <h:outputText
															value="Brewery Operation : #{homePageAction.pencomm10}" /></a>

												</div>

												<div class="row col-md-12"
													style="FONT-SIZE: large; background-color: #91fdd5;">
													<rich:spacer height="1px"></rich:spacer>
												</div>
												<div class="row col-md-12">

													<div class="col-md-4" align="left">
														<h:graphicImage value="/img/arrow.jpg"></h:graphicImage>
														<i> <h:outputText value="Status : "
																style="COLOR: #000040;" />
														</i>
													</div>

													<div class="col-md-2" align="right">
														<h:outputText value="Pending" style="COLOR: #006200;" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="Approved" style="COLOR: #006200;" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="Rejected" style="COLOR: #006200;" />
													</div>
													<div class="col-md-2" align="right">

														<h:outputText value="Total" style="COLOR: #006200;" />
													</div>

												</div>
												<div class="row col-md-12"
													style="FONT-SIZE: large; background-color: #91fdd5;">
													<rich:spacer height="1px"></rich:spacer>
												</div>
												<div class="row col-md-12">

													<div class="col-md-4" align="left">
														<h:outputText value="Industries Alcohol"
															style="FONT-SIZE: large; COLOR: #000040;" />
													</div>

													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.spiritalchopen}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.spiritalchoaprvd}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.spiritalchorej}" />
													</div>
													<div class="col-md-2" align="right">

														<h:outputText
															value="#{homePageAction.spiritalchorej+homePageAction.spiritalchopen+homePageAction.spiritalchoaprvd}" />
													</div>

												</div>
												<div class="row col-md-12">

													<div class="col-md-4" align="left">
														<h:outputText value="Distillery Estb."
															style="FONT-SIZE: large;  COLOR: #000040;" />
													</div>

													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.distestbpen}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.distestbaprvd}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.distestbrej}" />
													</div>
													<div class="col-md-2" align="right">

														<h:outputText
															value="#{homePageAction.distestbrej+homePageAction.distestbaprvd+homePageAction.distestbpen}" />
													</div>

												</div>
												<div class="row col-md-12" style="FONT-SIZE: large; COLOR: #000040;">

													<div class="col-md-4" align="left">
														<h:outputText value="Distillery Operation"
															style="FONT-SIZE: large;  COLOR: #000040;" />
													</div>

													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.distoppen}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.distopaprvd}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.distoprej}" />
													</div>
													<div class="col-md-2" align="right">

														<h:outputText
															value="#{homePageAction.distoprej+homePageAction.distoprej+homePageAction.distoppen}" />

													</div>

												</div>
												<div class="row col-md-12" style="FONT-SIZE: large; COLOR: #000040;">

													<div class="col-md-4" align="left">
														<h:outputText value="Brewery Estb."
															style="FONT-SIZE: large; COLOR: #000040;" />
													</div>

													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.brewestbopn}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.brewestbopn}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.brewestbrej}" />
													</div>
													<div class="col-md-2" align="right">

														<h:outputText
															value="#{homePageAction.brewestbopn+homePageAction.brewestbrej+homePageAction.brewestbopn}" />
													</div>

												</div>
												<div class="row col-md-12" style="FONT-SIZE: large; COLOR: #000040;">

													<div class="col-md-4" align="left">
														<h:outputText value="Brewery Operation"
															style="FONT-SIZE: large; COLOR: #000040;" />
													</div>

													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.brewoppend}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.brewopaprvd}" />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.brewoprej}" />
													</div>
													<div class="col-md-2" align="right">

														<h:outputText
															value="#{homePageAction.brewoprej+homePageAction.brewopaprvd+homePageAction.brewoppend}" />
													</div>

												</div>


												<div class="row col-md-12"
													style="FONT-SIZE: large; background-color: #91fdd5;">
													<rich:spacer height="1px"></rich:spacer>
												</div>
												<div class="  col-md-12">

													<div class="col-md-5" align="left">
														<h:graphicImage value="/img/arrow.jpg"></h:graphicImage>
														<i> <h:outputText value="Track Indents : "
																style="TEXT-DECORATION: underline; COLOR: #000040;" />
														</i>
													</div>

													<div class="col-md-6" align="left">
														<a target="_self"
															href="/portal/Licensing/Nivesh+Mitra/Tracking"><h:outputText
																value="Nivesh Mitra Tracking"
																style="FONT-STYLE: italic; color: #1520FE;" /> </a>
													</div>



													<div class="col-md-1" align="left"></div>

												</div>

											</div>

</div>
									 

									 
									 
								</h:panelGroup>
								<h:panelGroup rendered="#{homePageAction.liqourindntflg}">

									 
<div class="row"><rich:spacer height="15px"></rich:spacer></div>
								 
<div class="col-md-12 row  " align="center">
											<h:commandButton value="Back"
												style="display:block; width: 30%;"
												action="#{homePageAction.bck}"></h:commandButton>
										</div>

<div class="row"><rich:spacer height="15px"></rich:spacer></div>
								 
<div class="newsdiv  row" style="height: 400px;">


<div class="panel-heading" style="background-color: #b7dee8;">
												<a target="_self" href="/portal/Licensing/Janhit/Approval">LIQUOR INDENTS</a>
											</div>
												 
												<div class="panel-body">
										<div class="row">		<div class="col-md-4" align="left">

													<h:graphicImage value="/img/arrow.jpg"></h:graphicImage>
													<i> <h:outputText value="Unserved Indents: "
															style="TEXT-DECORATION: underline; COLOR: #000040; FONT-SIZE: large;" />
													</i>
												</div>
												<div class="  col-md-8" align="left"
													style="FONT-SIZE: small;">


													<a target="_self" 
														href="/portal/Licensing"><h:outputText style=" FONT-SIZE: large;"
															value="CL : #{homePageAction.clTot_P}" /></a>
													<rich:spacer width="10px"></rich:spacer>
													<a target="_self"  
														href="/portal/Licensing/"> <h:outputText style=" FONT-SIZE: large;"
															value="FL: #{homePageAction.flTot_P}" /></a>
													<rich:spacer width="10px"></rich:spacer>
													<a target="_self" 
														href="/portal/Licensing/"> <h:outputText style=" FONT-SIZE: large;"
															value="BEER : #{homePageAction.beerTot_P}" /></a>
												</div></div>
												<div class="row col-md-12" align="left"
													style="FONT-SIZE: small;"></div>

												<div class="row col-md-12"
													style="FONT-SIZE: large; background-color: #91fdd5;">
													<rich:spacer height="1px"></rich:spacer>
												</div>
												<div class="row col-md-12">

													<div class="col-md-2" align="left">
														<h:graphicImage value="/img/arrow.jpg"></h:graphicImage>
														<i> <h:outputText value="Detail" 
																style="COLOR: #000040;FONT-SIZE: large;" />
														</i>
													</div>


													<div class="col-md-1" align="right">
														<h:outputText value="0-3 Days"
															style="COLOR: #006200; FONT-SIZE: small;" />
													</div>
													<rich:spacer width="2px;" />
													<div class="col-md-1" align="right">
														<h:outputText value="4-6 Days"
															style="COLOR: #006200;  " />
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="7-10 Days"
															style="COLOR: #006200; " />
													</div>
													<div class="col-md-2" align="right">

														<h:outputText value="11-13 Days"
															style="COLOR: #006200;  " />
													</div>

													<div class="col-md-2" align="right">

														<h:outputText value="14-15 Days"
															style="COLOR: #006200;  " />
													</div>

													<div class="col-md-2" align="right">

														<h:outputText value="More than 15 Days"
															style="COLOR: #006200; " />
													</div>


												</div>
												<div class="row col-md-12"
													style="FONT-SIZE: large; background-color: #91fdd5;">
													<rich:spacer height="1px"></rich:spacer>
												</div>
												<div class="row col-md-12">

													<div class="col-md-2" align="left">
														<h:outputText value="CL"
															style="FONT-SIZE: large; COLOR: #000040;" />
													</div>

													<div class="col-md-1" align="right">
														<h:outputText value="#{homePageAction.cl_1Day}" style="FONT-SIZE: large;"/>
													</div>
													<rich:spacer width="2px;" />

													<div class="col-md-1" align="right">
														<h:outputText value="#{homePageAction.cl_2Day}"  style="FONT-SIZE: large;"/>
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.cl_3Day}" style="FONT-SIZE: large;" />
													</div>
													<div class="col-md-2" align="right">

														<h:outputText value="#{homePageAction.cl_4Day}" style="FONT-SIZE: large;"/>
													</div>


													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.cl_5Day}" style="FONT-SIZE: large;"/>
													</div>

													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.cl_MoreDay}" style="FONT-SIZE: large;"/>
													</div>


												</div>
												<div class="row col-md-12">

													<div class="col-md-2" align="left">
														<h:outputText value="FL"
															style="FONT-SIZE: large;  COLOR: #000040;"  />
													</div>

													<div class="col-md-1" align="right">
														<h:outputText value="#{homePageAction.fl_1Day}" style="FONT-SIZE: large;"/>
													</div>
													<rich:spacer width="2px;" />

													<div class="col-md-1" align="right">
														<h:outputText value="#{homePageAction.fl_2Day}" style="FONT-SIZE: large;"/>
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.fl_3Day}" style="FONT-SIZE: large;"/>
													</div>
													<div class="col-md-2" align="right">

														<h:outputText value="#{homePageAction.fl_4Day}" style="FONT-SIZE: large;"/>
													</div>

													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.fl_5Day}" style="FONT-SIZE: large;"/>
													</div>

													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.fl_MoreDay}" style="FONT-SIZE: large;"/>
													</div>


												</div>
												<div class="row col-md-12">

													<div class="col-md-2" align="left">
														<h:outputText value="BEER"
															style="FONT-SIZE: large;  COLOR: #000040;" />
													</div>

													<div class="col-md-1" align="right">
														<h:outputText value="#{homePageAction.beer_1Day}" style="FONT-SIZE: large;"/>
													</div>
													<rich:spacer width="2px;" />

													<div class="col-md-1" align="right">
														<h:outputText value="#{homePageAction.beer_2Day}" style="FONT-SIZE: large;"/>
													</div>
													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.beer_3Day}" style="FONT-SIZE: large;"/>
													</div>
													<div class="col-md-2" align="right">

														<h:outputText value="#{homePageAction.beer_4Day}" style="FONT-SIZE: large;"/>

													</div>

													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.beer_5Day}" style="FONT-SIZE: large;"/>
													</div>


													<div class="col-md-2" align="right">
														<h:outputText value="#{homePageAction.beer_MoreDay}" style="FONT-SIZE: large;"/>
													</div>


												</div>




												<div class="row col-md-12"
													style="FONT-SIZE: large; background-color: #91fdd5;">
													<rich:spacer height="1px"></rich:spacer>
												</div>
												<div class="  col-md-12">

													<div class="col-md-6" align="left">
														<h:graphicImage value="/img/arrow.jpg"></h:graphicImage>
														<i> <h:outputText value="Distillery Wise Pending: "
																style="TEXT-DECORATION: underline; COLOR: #000040; FONT-SIZE:small;" />
														</i>
													</div>

													<div class="col-md-2" align="left">
														<a target="_self"
															href="/portal/Licensing/Liquor+Indents/Distillery+Wise+CL+Pending"><h:outputText
																value="CL" style="FONT-STYLE: italic; color: #1520FE;" />
														</a>
													</div>


													<div class="col-md-2" align="left">
														<a target="_self"
															href="/portal/Licensing/Liquor+Indents/Distillery+Wise+FL+Pending"><h:outputText
																value="FL" style="FONT-STYLE: italic; color: #1520FE;" />
														</a>
													</div>

													<div class="col-md-2" align="left">
														<a target="_self"
															href="/portal/Licensing/Liquor+Indents/Brewery+Wise+Beer+Pending"><h:outputText
																value="BEER" style="FONT-STYLE: italic; color: #1520FE;" />
														</a>
													</div>
												</div>


												<!-- NEW -->
												<div class="row col-md-12"
													style="FONT-SIZE: large; background-color: #91fdd5;">
													<rich:spacer height="1px"></rich:spacer>
												</div>


												<div class="row col-md-12"
													style="FONT-SIZE: large; background-color: #91fdd5;">
													<rich:spacer height="1px"></rich:spacer>
												</div>
												<div class="row col-md-12" align="left">

													<h:graphicImage value="/img/arrow.jpg"></h:graphicImage>
													<i> <h:outputText value="Oldest Date: "
															style="TEXT-DECORATION: underline; COLOR: #000040;" />
													</i>
												</div>
												<div class="row col-md-12" align="left"
													style="FONT-SIZE: large; COLOR: #000040;">


													<a target="_self" 
														href="/portal/Licensing/Liquor+Indents/Distillery+Wise+CL+Pending"><h:outputText
															value="CL : #{homePageAction.cl_Oldest_Date}" /></a>
													<rich:spacer width="10px"></rich:spacer>
													<a target="_self" 
														href="/portal/Licensing/Liquor+Indents/Distillery+Wise+FL+Pending">
														<h:outputText
															value="FL : #{homePageAction.fl_Oldest_Date}" />
													</a>
													<rich:spacer width="10px"></rich:spacer>
													<a target="_self" 
														href="/portal/Licensing/Liquor+Indents/Brewery+Wise+Beer+Pending">
														<h:outputText
															value="Beer : #{homePageAction.beer_Oldest_Date}" />
													</a>
												</div>


												<!-- END NEW -->

											</div>
									 </div>
									 
								</h:panelGroup>
							</div>

							


						</div>
					</div>
				</h:panelGroup>
			</h:form>


	</f:view>

</ui:composition>
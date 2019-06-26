<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<header id="header">
	<div class="inner">
		<div class="pull-left">
			<h1 class="logo"><a href="#"><img src="/resources/images/top_logo.png" alt="HUB POP"></a></h1>
		</div>
		<div class="pull-right">
			<div class="align_r">
				<div class="col col-my">
					<div class="dropdown">
						<button id="btn-my" type="button" class="btn" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<p class="name">홍길동님</p>
							<span class="ico arr"></span>
						</button>
						<ul class="dropdown-menu dropdown-menu-right" role="btn-my" aria-labelledby="btn-my">
							<li><a href="#"><span class="ico ico4"></span>로그아웃</a></li>
						</ul> 
					</div>
				</div>

				<!-- 반응형 메뉴버튼 -->
				<div class="m_btn_wrap">
					<button id="addClassBtn" type="button"></button>
				</div>
				<!-- //반응형 메뉴버튼 -->

				<!-- 반응형 메뉴영역 -->
				<div class="m_menu_wrap">
					<div class="m_menu_area">
						<div class="m_menu_top">
							<h1 class="logo"><a href="#"><img src="/resources/images/top_logo.png" alt="HUB POP"></a></h1>
							<button class="closed" type="button"></a>
						</div>
						<div class="m_memu_btn">
							<div class="move_app">
								<a href="">HOME</a>
							</div>
							<div class="col col-my">
								<div class="dropdown">
									<button id="btn-my" type="button" class="btn" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
										<p class="name">홍길동님</p>
										<span class="ico arr"></span>
									</button>
									<ul class="dropdown-menu dropdown-menu-right" role="btn-my" aria-labelledby="btn-my">
										<li><a href="#"><span class="ico ico1"></span>개인정보 수정</a></li>
										<li><a href="#"><span class="ico ico3"></span>공지사항</a></li>
										<li><a href="#"><span class="ico ico4"></span>로그아웃</a></li>
									</ul> 
								</div>
							</div>
						</div>

						<div class="gnbMenu_m">
							<div class="gnb-in" id="mobileMenu">
								<!-- depth1 START -->
								<ul class="gnb-menu-m dept1 depth1">
									<li class="dept1">
										<a class="dept1 on" href="javascript:void(0);" onclick="depth1Click_m(event);"><span class="ico ico-bul ico1"></span>상품 목록</a>
									</li>
									<li class="dept1">
										<a class="dept1" href="javascript:void(0);" onclick="depth1Click_m(event);"><span class="ico ico-bul ico2"></span>마이 페이지<span class="ico ico-arr"></span></a>
										<ul class="dept2" style="display:none;">
											<li class="dept2">
												<a class="dept2" href="">사용 상품</a>
											</li>
											<li class="dept2">
												<a class="dept2" href="">요금 계산</a>
											</li>
										</ul>
										<!-- dept2 END -->
									</li>
								</ul>
								<!-- depth1 END -->
							</div>
						</div>
					</div>
				</div>
				<!-- //반응형 메뉴영역 -->
			</div>
		</div>
	</div>
</header>
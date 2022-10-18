import { useEffect, useRef, useState } from "react";

import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import ArrowForwardIcon from "@mui/icons-material/ArrowForward";
import ArrowDropDownIcon from "@mui/icons-material/ArrowDropDown";
// Import Swiper React components
import { Swiper, SwiperSlide } from "swiper/react";

// import required modules
import SwiperCore, { Autoplay, Navigation, Pagination, Keyboard } from "swiper";

// Import Swiper styles
import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";

export default function Recomm2({ tab, handleTab }) {
  SwiperCore.use([Autoplay, Navigation, Pagination]);
  const [swiper, setSwiper] = useState(null);
  const navigationPrevRef = useRef(null);
  const navigationNextRef = useRef(null);
  const [recommToggle, setRecommToggle] = useState(true);
  const [reviewToggle, setReviewToggle] = useState(true);

  function handleRecToggle() {
    if (tab === "맞춤" && recommToggle) {
      setRecommToggle(false);
    } else {
      setRecommToggle(true);
    }
  }

  function handleRevToggle() {
    if (tab === "리뷰" && reviewToggle) {
      setReviewToggle(false);
    } else {
      setReviewToggle(true);
    }
  }

  return (
    <>
      {/* notice */}
      <section className="notice">
        <div className="notice-line">
          <div className="bg-left"></div>
          <div className="bg-right"></div>
          <div className="inner">
            <div
              className="inner__left"
              onClick={() => {
                handleTab("맞춤");
                handleRecToggle();
              }}
            >
              <h2>맞춤 추천</h2>
              <div className="toggle-promotion">
                <div className="material-icons">
                  <ArrowDropDownIcon style={{ color: "#fff" }} />
                </div>
              </div>
            </div>
            <div
              className="inner__right"
              onClick={() => {
                handleTab("리뷰");
                handleRevToggle();
              }}
            >
              <h2>리뷰 추천</h2>
              <div className="toggle-promotion">
                <div className="material-icons">
                  <ArrowDropDownIcon />
                </div>
              </div>
            </div>
          </div>
        </div>

        {tab === "맞춤" && (
          <>
            <div className={recommToggle ? "promotion" : "promotion hide"}>
              <div className="swiper-container">
                <Swiper
                  className="swiper-wrapper"
                  spaceBetween={10}
                  centeredSlides={true}
                  loop={true} // 슬라이드 반복 여부
                  slidesPerView={3} // 한 슬라이드에 보여줄 갯수
                  cssMode={true}
                  navigation={{
                    prevEl: navigationPrevRef.current,
                    nextEl: navigationNextRef.current,
                  }}
                  onInit={(swiper) => {
                    swiper.params.navigation.prevEl = navigationPrevRef.current;
                    swiper.params.navigation.nextEl = navigationNextRef.current;

                    swiper.navigation.init();
                    swiper.navigation.update();
                  }}
                  onSwiper={setSwiper}
                  autoplay={{
                    delay: 2500,
                  }}
                  pagination={true}
                  keyboard={true}
                  modules={[Autoplay, Navigation, Pagination, Keyboard]}
                  ref={setSwiper}
                >
                  <SwiperSlide className="swiper-slide">
                    <img
                      src="/images/promotion_slide3.jpg"
                      alt="2022 뉴이어, 스타벅스와 함께 즐겁고 활기차게 시작하세요!"
                    />
                  </SwiperSlide>
                </Swiper>
              </div>

              <div className="swiper-pagination"></div>
              <div className="swiper-prev">
                <div className="material-icons" style={{ display: "flex" }}>
                  <ArrowBackIcon ref={navigationPrevRef} />
                </div>
              </div>
              <div className="swiper-next">
                <div className="material-icons" style={{ display: "flex" }}>
                  <ArrowForwardIcon ref={navigationNextRef} />
                </div>
              </div>
            </div>
          </>
        )}
        {tab === "리뷰" && (
          <>
            <div className={reviewToggle ? "promotion" : "promotion hide"}>
              <div className="swiper-container">
                <Swiper
                  className="swiper-wrapper"
                  spaceBetween={10}
                  centeredSlides={true}
                  loop={true} // 슬라이드 반복 여부
                  slidesPerView={3} // 한 슬라이드에 보여줄 갯수
                  cssMode={true}
                  navigation={{
                    prevEl: navigationPrevRef.current,
                    nextEl: navigationNextRef.current,
                  }}
                  onInit={(swiper) => {
                    swiper.params.navigation.prevEl = navigationPrevRef.current;
                    swiper.params.navigation.nextEl = navigationNextRef.current;

                    swiper.navigation.init();
                    swiper.navigation.update();
                  }}
                  onSwiper={setSwiper}
                  autoplay={{
                    delay: 2500,
                  }}
                  pagination={true}
                  keyboard={true}
                  modules={[Autoplay, Navigation, Pagination, Keyboard]}
                  ref={setSwiper}
                >
                  <SwiperSlide className="swiper-slide">
                    <img
                      src="/images/promotion_slide3.jpg"
                      alt="2022 뉴이어, 스타벅스와 함께 즐겁고 활기차게 시작하세요!"
                    />
                  </SwiperSlide>
                  <SwiperSlide className="swiper-slide">
                    <img
                      src="/images/promotion_slide4.jpg"
                      alt="2022 뉴이어, 스타벅스와 함께 즐겁고 활기차게 시작하세요!"
                    />
                  </SwiperSlide>
                  <SwiperSlide className="swiper-slide">
                    <img
                      src="/images/promotion_slide5.jpg"
                      alt="2022 뉴이어, 스타벅스와 함께 즐겁고 활기차게 시작하세요!"
                    />
                  </SwiperSlide>
                </Swiper>
              </div>

              <div className="swiper-pagination"></div>
              <div className="swiper-prev">
                <div className="material-icons" style={{ display: "flex" }}>
                  <ArrowBackIcon ref={navigationPrevRef} />
                </div>
              </div>
              <div className="swiper-next">
                <div className="material-icons" style={{ display: "flex" }}>
                  <ArrowForwardIcon ref={navigationNextRef} />
                </div>
              </div>
            </div>
          </>
        )}
      </section>

      <style jsx>{`
        /* NOTICE */
        .notice {
        }
        .notice .notice-line {
          position: relative;
        }
        .notice .notice-line .bg-left {
          position: absolute;
          top: 0;
          left: 0;
          width: 50%;
          height: 100%;
          background-color: #333;
        }
        .notice .notice-line .bg-right {
          position: absolute;
          top: 0;
          right: 0;
          width: 50%;
          height: 100%;
          background-color: #f6f5ef;
        }
        .notice .notice-line .inner {
          width: 1000px;
          margin: 0 auto;
          position: relative;
          height: 62px;
          z-index: 1;
          display: flex;
        }
        .notice .notice-line .inner__left {
          width: 50%;
          height: 100%;
          background-color: #333;
          display: flex;
          align-items: center;
        }
        .notice .notice-line .inner__left h2 {
          color: #fff;
          font-size: 17px;
          font-weight: 700;
          margin-right: 20px;
        }
        .notice .notice-line .inner__left .swiper-container {
          height: 62px;
          flex-grow: 1;
        }
        .notice .notice-line .inner__left .swiper-slide {
          height: 62px;
          display: flex;
          align-items: center;
        }
        .notice .notice-line .inner__left .swiper-slide a {
          color: #fff;
        }
        .notice .notice-line .inner__left .notice-line__more {
          width: 62px;
          height: 62px;
          display: flex;
          justify-content: center;
          align-items: center;
        }
        .notice .notice-line .inner__left .notice-line__more .material-icons {
          color: #fff;
          font-size: 30px;
        }
        .notice .notice-line .inner__right {
          width: 50%;
          height: 100%;
          display: flex;
          justify-content: flex-end;
          align-items: center;
        }
        .notice .notice-line .inner__right h2 {
          font-size: 17px;
          font-weight: 700;
        }
        .notice .notice-line .inner__right .toggle-promotion {
          width: 62px;
          height: 62px;
          cursor: pointer;
          display: flex;
          justify-content: center;
          align-items: center;
        }
        .notice .notice-line .inner__right .toggle-promotion .material-icons {
          font-size: 30px;
        }
        .notice .promotion {
          height: 693px;
          background-color: #f6f5ef;
          position: relative;
          transition: height 0.4s;
          overflow: hidden;
        }
        .notice .promotion.hide {
          height: 0;
        }
        .notice .promotion .swiper-container {
          width: calc(819px * 3 + 20px);

          height: 553px;
          position: absolute;
          top: 40px;
          left: 50%;
          margin-left: calc((819px * 3 + 20px) / -2);
        }
        .notice .promotion .swiper-slide {
          opacity: 0.5;
          transition: opacity 1s;
          position: relative;
        }
        .notice .promotion .swiper-slide-active {
          opacity: 1;
        }
        .notice .promotion .swiper-slide .btn {
          position: absolute;
          bottom: 0;
          left: 0;
          right: 0;
          margin: auto;
        }
        .notice .promotion .swiper-pagination {
          bottom: 40px;
          left: 0;
          right: 0;
        }
        .notice .promotion .swiper-pagination .swiper-pagination-bullet {
          background-color: transparent;
          background-image: url("/images/promotion_slide_pager.png");
          width: 12px;
          height: 12px;
          margin-right: 6px;
          outline: none;
        }
        .notice
          .promotion
          .swiper-pagination
          .swiper-pagination-bullet:last-child {
          margin-right: 0;
        }
        .notice .promotion .swiper-pagination .swiper-pagination-bullet-active {
          background-image: url("/images/promotion_slide_pager_on.png");
        }
        .notice .promotion .swiper-prev,
        .notice .promotion .swiper-next {
          width: 42px;
          height: 42px;
          border: 2px solid #333;
          border-radius: 50%;
          position: absolute;
          top: 300px;
          z-index: 1;
          cursor: pointer;
          outline: none;
          display: flex;
          justify-content: center;
          align-items: center;
          transition: 0.4s;
        }
        .notice .promotion .swiper-prev {
          left: 50%;
          margin-left: -480px;
        }
        .notice .promotion .swiper-next {
          right: 50%;
          margin-right: -480px;
        }
        .notice .promotion .swiper-prev:hover,
        .notice .promotion .swiper-next:hover {
          background-color: #333;
          color: #fff;
        }
      `}</style>
    </>
  );
}

import { useEffect } from "react";
import Swiper from "swiper";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import ArrowForwardIcon from "@mui/icons-material/ArrowForward";
import ArrowDropDownIcon from "@mui/icons-material/ArrowDropDown";

export default function Recomm({ tab, handleTab }) {
  useEffect(() => {
    // new Swiper(선택자, 옵션)
    new Swiper(".notice-line .swiper-container", {
      direction: "vertical",
      autoplay: true,
      loop: true,
    });
    new Swiper(".promotion .swiper-container", {
      slidesPerView: 3,
      spaceBetween: 10,
      centeredSlides: true,
      loop: true,
      autoplay: {
        delay: 3000,
      },
      pagination: {
        el: ".promotion .swiper-pagination", // 페이지 번호 요소 선택자
        clickable: true, // 사용자의 페이지 번호 요소 제어 가능 여부
      },
      navigation: {
        prevEl: ".promotion .swiper-prev",
        nextEl: ".promotion .swiper-next",
      },
    });

    const promotionEl = document.querySelector(".promotion");
    const promotionToggleBtn = document.querySelector(".toggle-promotion");
    const recommandToggleBtn = document.querySelector(".toggle-recommand");
    let isHidePromotion = false;
    promotionToggleBtn.addEventListener("click", function () {
      isHidePromotion = !isHidePromotion;
      if (isHidePromotion) {
        // 숨김 처리!
        promotionEl.classList.add("hide");
      } else {
        // 보임 처리!
        promotionEl.classList.remove("hide");
      }
    });
  }, []);

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

        <div className="promotion">
          <div className="swiper-container">
            <div className="swiper-wrapper">
              {tab === "리뷰" ? (
                <>
                  <div className="swiper-slide">
                    <img
                      src="/images/promotion_slide1.jpg"
                      alt="2022 뉴이어, 스타벅스와 함께 즐겁고 활기차게 시작하세요!"
                    />
                  </div>
                  <div className="swiper-slide">
                    <img
                      src="/images/promotion_slide2.jpg"
                      alt="2022 뉴이어, 스타벅스와 함께 즐겁고 활기차게 시작하세요!"
                    />
                  </div>
                </>
              ) : (
                <>
                  <div className="swiper-slide">
                    <img
                      src="/images/promotion_slide3.jpg"
                      alt="2022 뉴이어, 스타벅스와 함께 즐겁고 활기차게 시작하세요!"
                    />
                  </div>
                  <div className="swiper-slide">
                    <img
                      src="/images/promotion_slide4.jpg"
                      alt="2022 뉴이어, 스타벅스와 함께 즐겁고 활기차게 시작하세요!"
                    />
                  </div>
                  <div className="swiper-slide">
                    <img
                      src="/images/promotion_slide5.jpg"
                      alt="2022 뉴이어, 스타벅스와 함께 즐겁고 활기차게 시작하세요!"
                    />
                  </div>
                </>
              )}
            </div>
          </div>
          <div className="swiper-pagination"></div>
          <div className="swiper-prev">
            <div className="material-icons" style={{ display: "flex" }}>
              <ArrowBackIcon />
            </div>
          </div>
          <div className="swiper-next">
            <div className="material-icons" style={{ display: "flex" }}>
              <ArrowForwardIcon />
            </div>
          </div>
        </div>
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

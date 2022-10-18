import { useEffect } from "react";
import { gsap } from "gsap";
import ReactPlayer from "react-player";

export default function Introduction() {
  useEffect(() => {}, []);

  return (
    <>
      <section className="season-product scroll-spy">
        <div className="inner">
          <img
            src="/main3.png"
            width="400px"
            alt=""
            className="product back-to-position to-right delay-0"
          />

          <div className="text-group">
            <img
              src="/images/season_product_text1.png"
              alt=""
              className="title back-to-position to-left delay-1"
            />
            <img
              src="/images/season_product_text2.png"
              alt=""
              className="description back-to-position to-left delay-2"
            />
            <div className="more back-to-position to-left delay-3"></div>
          </div>
        </div>
      </section>

      <section className="reserve-coffee scroll-spy">
        <div className="inner">
          <div className="text-group">
            <img
              src="/images/reserve_text.png"
              alt=""
              className="description back-to-position to-right delay-1"
            />
            <div className="more back-to-position to-right delay-2"></div>
          </div>

          <img
            width="400px"
            src="/main4.png"
            alt=""
            className="product back-to-position to-left delay-3"
          />
        </div>
      </section>

      <section className="find-store scroll-spy">
        <div className="inner">
          <img
            src="/images/find_store_texture1.png"
            alt=""
            className="texture1"
          />

          <img
            width="350px"
            src="/main5.png"
            alt=""
            className="picture picture1 back-to-position to-right delay-0"
          />
          <img
            width="216px"
            src="/main6.png"
            alt=""
            className="picture picture2 back-to-position to-right delay-1"
          />

          <div className="text-group">
            <img
              src="/images/find_store_text1.png"
              alt=""
              className="title back-to-position to-left delay-0"
            />
            <img
              src="/images/find_store_text2.png"
              alt=""
              className="description back-to-position to-left delay-1"
            />
            <div className="more back-to-position to-left delay-2"></div>
          </div>
        </div>
      </section>
      <style jsx>{`
        .season-product {
          background-image: url("/images/season_product_bg.jpg");
        }
        .season-product .inner {
          width: 1100px;
          margin: 0 auto;
          position: relative;
          height: 400px;
        }
        .season-product .floating3 {
          position: absolute;
          top: -200px;
          right: 0;
        }
        .season-product .text-group {
          position: absolute;
          top: 110px;
          right: 100px;
        }
        .season-product .text-group .title {
          margin-bottom: 10px;
        }
        .season-product .text-group .description {
          margin-bottom: 15px;
        }
        .season-product .text-group .more {
        }

        .reserve-coffee {
          background-image: url("/images/reserve_bg.jpg");
        }
        .reserve-coffee .inner {
          width: 1100px;
          margin: 0 auto;
          position: relative;
          height: 400px;
        }
        .reserve-coffee .reserve-logo {
          position: absolute;
          top: 110px;
          left: 0;
        }
        .reserve-coffee .text-group {
          position: absolute;
          top: 124px;
          left: 208px;
        }
        .reserve-coffee .product {
          position: absolute;
          top: 0;
          right: 0;
        }

        .find-store {
          background-image: url("/images/find_store_bg.jpg");
        }
        .find-store .inner {
          height: 400px;
          width: 1100px;
          margin: 0 auto;
          position: relative;
        }
        .find-store .texture1 {
          position: absolute;
          top: 0;
          left: 400px;
        }
        .find-store .texture2 {
          position: absolute;
          bottom: 0;
          right: 0;
        }
        .find-store .picture {
          position: absolute;
        }
        .find-store .picture1 {
          top: -60px;
          left: 0;
        }
        .find-store .picture2 {
          top: 150px;
          left: 250px;
        }
        .find-store .text-group {
          position: absolute;
          top: 120px;
          left: 550px;
        }
        .find-store .text-group .title {
          margin-bottom: 20px;
        }
        .find-store .text-group .description {
          margin-bottom: 20px;
        }
      `}</style>
    </>
  );
}

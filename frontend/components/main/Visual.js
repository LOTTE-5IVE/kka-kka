import { useLayoutEffect, useRef } from "react";
import { gsap } from "gsap";

export default function Visual() {
  const fadeEls = useRef([]);

  useLayoutEffect(() => {
    fadeEls.current.map((fadeEl, index) => {
      gsap.to(fadeEl, 1, {
        delay: (index + 1) * 0.7,
        opacity: 1,
      });
    });
  });

  return (
    <>
      <section className="visual">
        <div className="inner">
          <div className="fade-in" ref={(el) => (fadeEls.current[1] = el)}>
            <img src="/giftset.png" className="giftset" />
          </div>
          <div className="fade-in" ref={(el) => (fadeEls.current[2] = el)}>
            <img width="500px" src="/cookie.png" className="cookie" />
          </div>
          <div className="fade-in" ref={(el) => (fadeEls.current[3] = el)}>
            <img width="420px" src="/bts.png" className="bts" />
          </div>
          <div className="fade-in" ref={(el) => (fadeEls.current[4] = el)}>
            <div className="logoComment">
              <img width="845px" src="/intro.png" />
            </div>
          </div>
        </div>
      </section>

      <style jsx>{`
        .visual {
          background-image: url("/main/visual_bg.jpg");
          background-position: center;

          .inner {
            width: 1100px;
            margin: 0 auto;
            position: relative;
            height: 646px;

            .title {
              position: absolute;
              top: 15px;
              left: -80px;
            }
          }

          .giftset {
            position: absolute;
            bottom: 15px;
            right: -200px;
          }

          .cookie {
            position: absolute;
            bottom: 0;
            right: 105px;
          }

          .logoComment {
            position: absolute;
            top: 40px;
            right: 500px;
          }

          .bts {
            position: absolute;
            bottom: -10px;
            left: 280px;
          }

          .fade-in {
            opacity: 0;
          }
        }
      `}</style>
    </>
  );
}

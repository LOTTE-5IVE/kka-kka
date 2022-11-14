import { useState, useRef } from "react";
import useResizeObserver from "./useResizeObserver";

export const Ellipsis = ({ text }) => {
  const contentRef = useRef(null);
  const [isShowReadMore, setIsShowReadMore] = useState(false);
  const observeCallback = (entries) => {
    for (let entry of entries) {
      if (entry.target.scrollHeight > entry.contentRect.height) {
        setIsShowReadMore(true);
      } else {
        setIsShowReadMore(false);
      }
    }
  };
  useResizeObserver({ callback: observeCallback, element: contentRef });
  const onClick = (e) => {
    contentRef.current.classList.add("show");
    setIsShowReadMore(false);
  };
  return (
    <div>
      <div className="ellipsis" ref={contentRef}>
        <p style={{ wordBreak: "break-all", textAlign: "left", margin: "0" }}>
          {text}
        </p>
      </div>
      {isShowReadMore && (
        <p
          className="button"
          onClick={onClick}
          style={{ textAlign: "left", margin: "0", cursor: "pointer" }}
        >
          ...더보기
        </p>
      )}

      <style jsx>{`
        .ellipsis {
          position: relative;
          display: -webkit-box;
          max-height: 2rem;
          line-height: 2rem;
          overflow: hidden;
          -webkit-line-clamp: 1;
          &.show {
            display: block;
            max-height: none;
            overflow: auto;
            -webkit-line-clamp: unset;
          }
        }

        .button {
          max-height: 2rem;
          line-height: 2rem;
          &.hide {
            display: none;
          }
        }
      `}</style>
    </div>
  );
};

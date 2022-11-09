import { useEffect, useRef } from "react";
import ResizeObserver from "resize-observer-polyfill";

const useResizeObserver = ({ callback, element }) => {
  const current = element && element.current;
  const observer = useRef(null);

  useEffect(() => {
    if (observer && observer.current && current) {
      observer.current.unobserve(current);
    }
    const observe = () => {
      if (element && element.current && observer.current) {
        observer.current.observe(element.current);
      }
    };
    const resizeObserverOrPolyfill = ResizeObserver;
    observer.current = new resizeObserverOrPolyfill(callback);
    observe();

    return () => {
      if (observer && observer.current && element && element.current) {
        observer.current.unobserve(element.current);
      }
    };
  }, [callback, current, element]);
};

export default useResizeObserver;

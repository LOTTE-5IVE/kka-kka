import StarIcon from "./StartIcon";

const RangeWithIcons = ({
  labelText,
  color = "red",
  min = 0,
  max,
  step,
  value,
  disabled,
  setValue,
  onStart,
  onEnd,
  readOnly,
  borderColor,
  starWidth,
}) => {
  const onChange = (event) => {
    setValue?.(Number(event.target.value));
  };

  const getIconsStatus = (index) => {
    if (value === 0) {
      return "EMPTY";
    }

    if (index < value) {
      let rating_remainder = Math.round((value - index) * 100) / 100;
      if (0 < rating_remainder && rating_remainder <= 0.3) {
        return "SMALL_HALF";
      }
      if (0.7 <= rating_remainder && rating_remainder <= 0.9) {
        return "BIG_HALF"
      }
      if (0.4 <= rating_remainder && rating_remainder <= 0.6) {
        return "HALF"
      }
      return "FULL";
    }

    return "EMPTY";
  };

  return (
    <>
      <div className="wrapper">
        <span>{labelText}</span>
        <input
          type="range"
          min={min}
          max={max}
          step={step}
          value={value}
          onChange={(event) => !disabled && onChange(event)}
          onTouchStart={onStart}
          onKeyDown={onStart}
          onMouseDown={onStart}
          onTouchEnd={onEnd}
          onMouseUp={onEnd}
          onKeyUp={onEnd}
          readOnly={readOnly}
          tabIndex={readOnly ? -1 : 0}
        />
        <div>
          {Array.from({ length: 5 }, (_, i) => i + 1).map((_, index) => {
            return (
              <StarIcon
                borderColor={borderColor}
                color={color}
                key={index}
                width={starWidth}
                status={getIconsStatus(index)}
              />
            );
          })}
        </div>
      </div>
      <style jsx>{`
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          span {
            width: 0;
            height: 0;
            opacity: 0;
          }

          input {
            width: 125px;
            height: 100%;
            z-index: 2;
            position: absolute;
            top: 0;

            cursor: pointer;
            margin: 0;
            appearance: none;
            opacity: 0;
            outline: 0;
          }

          input[type="range"]::-webkit-slider-runnable-track {
            width: 100%;
            height: 3rem;
            cursor: pointer;
            background-color: grey;
          }

          input::-webkit-slider-thumb {
            height: 3.5rem;
            width: 1rem;
            margin-left: 0.5rem;
          }

          div {
            width: 100%;
            height: 3rem;
            margin: 0 auto;
            display: flex;
            position: relative;
            align-items: center;
            justify-content: center;
          }
        }

        @media screen and (max-width: 768px) {
          /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
          span {
            width: 0;
            height: 0;
            opacity: 0;
          }

          input {
            width: 6.58vw;
            height: 100%;
            z-index: 2;
            position: absolute;
            top: 0;

            cursor: pointer;
            margin: 0;
            appearance: none;
            opacity: 0;
            outline: 0;
          }

          input[type="range"]::-webkit-slider-runnable-track {
            width: 100%;
            height: 3rem;
            cursor: pointer;
            background-color: grey;
          }

          input::-webkit-slider-thumb {
            height: 3.5rem;
            width: 1rem;
            margin-left: 0.5rem;
          }

          div {
            width: 10vw;
            height: 3vw;
            margin: 0 auto;
            display: flex;
            position: relative;
            align-items: center;
            justify-content: center;
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          span {
            width: 0;
            height: 0;
            opacity: 0;
          }

          input {
            width: 125px;
            height: 100%;
            z-index: 2;
            position: absolute;
            top: 0;

            cursor: pointer;
            margin: 0;
            appearance: none;
            opacity: 0;
            outline: 0;
          }

          input[type="range"]::-webkit-slider-runnable-track {
            width: 100%;
            height: 3rem;
            cursor: pointer;
            background-color: grey;
          }

          input::-webkit-slider-thumb {
            height: 3.5rem;
            width: 1rem;
            margin-left: 0.5rem;
          }

          div {
            width: 100%;
            height: 3rem;
            margin: 0 auto;
            display: flex;
            position: relative;
            align-items: center;
            justify-content: center;
          }
        }
      `}</style>
    </>
  );
};

export default RangeWithIcons;

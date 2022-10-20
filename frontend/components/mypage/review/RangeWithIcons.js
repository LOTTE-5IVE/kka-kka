import StarIcon from './StartIcon';

const RangeWithIcons = ({
                          labelText,
                          color = 'red',
                          min = 0,
                          max,
                          step,
                          value,
                          disabled,
                          setValue,
                          onStart,
                          onEnd,
                          readOnly,
                        }) => {

  const onChange = (event) => {
    setValue?.(Number(event.target.value));
  };

  const getIconsStatus = (index) => {
    if (value === 0) {
      return 'EMPTY';
    }

    if (index < value) {
      if (index === Math.floor(value)) {
        return 'HALF';
      }

      return 'FULL';
    }

    return 'EMPTY';
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
          {Array.from({length: 5}, (_, i) => i + 1).map((_, index) => {
            return <StarIcon color={color} key={index} status={getIconsStatus(index)}/>;
          })}
        </div>
      </div>
      <style jsx>{`
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

        input[type='range']::-webkit-slider-runnable-track {
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
      `}</style>
    </>
  );
};

export default RangeWithIcons;

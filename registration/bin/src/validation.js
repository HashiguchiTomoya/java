
function updateProgressBar(stepIndex) {
  const steps = document.querySelectorAll("#progressBar .step");
  steps.forEach((step, index) => {
    step.classList.toggle("active", index === stepIndex);
  });
}

function nextStep(currentStep)
{
	const fieldsets = document.querySelectorAll("form fieldset");
	if(validateStep(fieldsets[currentStep -1]))
	{
		fieldsets[currentStep - 1].style.display = "none";
		fieldsets[currentStep].style.display = "block";
		updateProgressBar(currentStep);
	}
}

function validateStep(fieldset)
{
	const inputs = fieldset.querySelectorAll("input, select");
	for(let input of inputs)
	{
		if(!input.checkValidity())
		{
			input.reportValidity();
			return false;
		}
	}
	
	return true;
}

document.getElementById("registrationForm").addEventListener("submit", function (e)
{
	const lastStep = document.querySelectorAll("form fieldset")[2];
	if (!validateStep(lastStep))
	{
		e.preventDefault();
	}
	else
	{
		alert("登録が完了しました！");
	}
});
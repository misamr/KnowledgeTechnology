graph[
    node[
        id 1
        label "Do you have any health problems or injuries?"
        display "radio"
    ]
    node[
        id 2
        label "How long do you want to workout for?"
        display "radio"
    ]
    node[
        id 3
        label "Which age group do you belong to?"
        display "radio"
    ]
    node[
        id 4
        label "What is your goal?"
        display "radio"
    ]
    node[
        id 5
        label "What is your level of experience?"
        display "radio"
    ]
    node[
        id 6
        label "Which part of the body do you want to focus on?"
        display "checkbox"
    ]
    node[
        id 7
        label "exit"
        display "terminate"
    ]
    edge[
        source 1
        target 7
        label "Yes"
    ]
    edge[
        source 1
        target 2
        label "No"
    ]
    edge[
        source 2
        target 3
        label "30 minutes"
    ]
    edge[
        source 2
        target 3
        label "45 minutes"
    ]
    edge[
        source 2
        target 3
        label "60 minutes (1 hour)"
    ]
    edge[
        source 2
        target 3
        label "90 minutes (1 hour and 30 minutes)"
    ]
    edge[
         source 2
         target 3
         label "120 minutes (2 hours)"
    ]
    edge[
        source 3
        target 7
        label "less than 18 years old"
    ]
    edge[
        source 3
        target 4
        label "18 to 49 years old"
    ]
    edge[
        source 3
        target 7
        label "50 years or older"
    ]
    edge[
        source 4
        target 5
        label "Weight Loss"
    ]
    edge[
        source 4
        target 6
        label "Muscle Gain"
    ]
    edge[
        source 4
        target 5
        label "Increase Endurance"
    ]
    edge[
        source 6
        target 5
        label "Upper"
    ]
    edge[
        source 6
        target 5
        label "Core"
    ]
    edge[
        source 6
        target 5
        label "Lower"
    ]
    edge[
        source 5
        target 7
        label "Beginner"
    ]
    edge[
        source 5
        target 7
        label "Advanced"
    ]
]